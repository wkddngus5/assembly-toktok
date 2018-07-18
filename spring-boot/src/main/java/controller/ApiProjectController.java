package controller;

import dao.CongressmenDao;
import dao.ProjectDao;
import dao.ProjectJoinDao;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class ApiProjectController {
    private static final long GOAL_COUNT = 1000;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectJoinDao projectJoinDao;

    @Autowired
    private CongressmenDao congressmenDao;

    @RequestMapping(value = "/projects/{id}/{manId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Project> post(@PathVariable("id") final Long id, @PathVariable("manId") final Long manId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Project project = projectDao.findById(id).orElse(null);
        if (project == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(project, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProjectResponse> insert(@RequestBody Project projectRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectRequest.setUser_id(principal.getId());

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        projectRequest.setCreated_at(createDate);
        projectRequest.setUpdated_at(createDate);
        projectRequest.setParticipations_count(0L);
        projectRequest.setParticipations_goal_count(GOAL_COUNT);

        Project project = projectDao.save(projectRequest);
        if (project == null) {
            return new ResponseEntity<>(new ProjectResponse("Fail"), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(new ProjectResponse(project.getId()), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/projects/join", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProjectJoinResponse> join(@RequestBody ProjectJoin reqeust) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reqeust.setUser_id(principal.getId());

        ProjectJoin projectJoin = projectJoinDao.selectByProjectIdAndUserId(reqeust.getProject_id(), reqeust.getUser_id());
        if (projectJoin == null) {
            String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            reqeust.setCreated_at(createDate);
            reqeust.setUpdated_at(createDate);

            projectJoin = projectJoinDao.save(reqeust);
            projectDao.addParticipation(reqeust.getProject_id());
            if (projectJoin == null) {
                return new ResponseEntity<>(new ProjectJoinResponse("", "Fail"), headers, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<>(new ProjectJoinResponse("Participation", null), headers, HttpStatus.OK);
            }
        } else {
            projectJoinDao.deleteById(projectJoin.getId());
            projectDao.removeParticipation(reqeust.getProject_id());

            return new ResponseEntity<>(new ProjectJoinResponse("Cancellation", null), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/projects/sorted", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Project>> sorted(@RequestParam("by") final String by,
                                                @RequestParam("number") final int number,
                                                @RequestParam(defaultValue = "all", value = "category", required = false) final String category) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        final List<String> validBy = Arrays.asList("new", "best", "imminent");
        final List<String> validCategory = Arrays.asList("environment", "humanRights", "parenting", "press", "all");

        if (!validBy.contains(by)) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        if (!validCategory.contains(category)) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        if (number < 1) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        String orderBy = "";
        if ("new".equals(by)) {
            orderBy = "created_at";
        } else if ("best".equals(by)) {
            orderBy = "participations_count";
        } else {
            orderBy = "deleted_at";
        }

        int start = number - 1;
        Pageable pq = PageRequest.of(start, 10, Sort.Direction.DESC, orderBy);

        String where = "1 = 1";
        if (!"all".equalsIgnoreCase(category)) {
            where = "category = " + category;
        }

        List<Project> projectList = projectDao.sorted(where, pq);

        return new ResponseEntity<>(projectList, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/projets/committees", method = RequestMethod.GET)
    public ResponseEntity<List<Congressmen>> post(@RequestParam("name") String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Congressmen> congressmen = congressmenDao.findByNameCongressmen(name);
        if (congressmen == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(congressmen, headers, HttpStatus.OK);
        }
    }
}

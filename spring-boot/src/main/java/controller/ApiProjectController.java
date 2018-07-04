package controller;

import dao.ProjectDao;
import domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ApiProjectController {
    @Autowired
    private ProjectDao projectDao;

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
}

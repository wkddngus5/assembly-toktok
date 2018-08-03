package controller;

import dao.ProjectDao;
import dao.TimelineDao;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ApiAdminProjectController {
    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private TimelineDao timelineDao;

    @RequestMapping(value = "/administrator/projects/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Project updateProject = projectDao.getOne(id);
        updateProject.updateProject(project);

        updateProject = projectDao.save(updateProject);
        if (updateProject == null) {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(updateProject, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/administrator/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Project> deleteProject(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Project deleteProject = projectDao.getOne(id);
        deleteProject.setDeleted_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

        deleteProject = projectDao.save(deleteProject);
        if (deleteProject == null) {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(deleteProject, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/administrator/projects/{id}/timelines", method = RequestMethod.POST)
    public ResponseEntity<Timeline> insertTimeLines(@PathVariable Long id, @RequestBody Timeline timeline) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Timeline createTimeLine = timelineDao.save(Timeline.createTimeline(principal.getNickname(), timeline.getImage(), timeline.getContents(), timeline.getSubject(), id, timeline.getDate()));
        if (createTimeLine != null) {
            return new ResponseEntity<>(createTimeLine, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/administrator/projects/{id}/timelines/{tid}", method = RequestMethod.PUT)
    public ResponseEntity<Timeline> updateTimeLines(@PathVariable Long id, @PathVariable Long tid, @RequestBody Timeline timeline) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Timeline updateTimeline = timelineDao.getOne(tid);
        updateTimeline.updateTimeline(timeline);

        updateTimeline = timelineDao.save(updateTimeline);
        if (updateTimeline != null) {
            return new ResponseEntity<>(updateTimeline, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/administrator/projects/{id}/timelines/{tid}", method = RequestMethod.DELETE)
    public ResponseEntity<Timeline> deleteTimeLines(@PathVariable Long id, @PathVariable Long tid) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Timeline deleteTimeline = Timeline.createTimeline(timelineDao.getOne(tid));

        timelineDao.deleteById(tid);
        if (deleteTimeline != null) {
            return new ResponseEntity<>(deleteTimeline, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
package controller;

import dao.ProjectDao;
import dao.TimelineDao;
import domain.ApiResult;
import domain.Project;
import domain.Timeline;
import domain.User;
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
    public ResponseEntity<ApiResult> updateProject(@PathVariable Long id, @RequestBody Project project) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(new ApiResult(true, "Update Project"), HttpStatus.OK);
    }

    @RequestMapping(value = "/administrator/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResult> deleteProject(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        projectDao.deleteById(id);

        return new ResponseEntity<>(new ApiResult(true, "Delete Project"), HttpStatus.OK);
    }

    @RequestMapping(value = "/administrator/projects/{id}/timelines", method = RequestMethod.POST)
    public ResponseEntity<Timeline> insertTimeLines(@PathVariable Long id, @RequestBody Timeline timeline) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Timeline createTimeLine = timelineDao.save(Timeline.createTimeLine(principal.getNickname(), timeline.getImage(), timeline.getBody(), id, timeline.getCongressman_id(), timeline.getTimeline_date()));
        if (createTimeLine != null) {
            return new ResponseEntity<>(createTimeLine, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/administrator/projects/{id}/timelines/{tid}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResult> updateTimeLines(@PathVariable Long id, @PathVariable Long tid, @RequestBody Timeline timeline) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        timelineDao.updateTimelinkeById(tid, timeline.getBody(), timeline.getImage(), timeline.getCongressman_id(), timeline.getTimeline_date(), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

        return new ResponseEntity<>(new ApiResult(true, "Update TimeLine"), HttpStatus.OK);
    }

    @RequestMapping(value = "/administrator/projects/{id}/timelines/{tid}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResult> deleteTimeLines(@PathVariable Long id, @PathVariable Long tid) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        timelineDao.deleteById(tid);

        return new ResponseEntity<>(new ApiResult(true, "Delete TimeLine"), HttpStatus.OK);
    }
}

package controller;

import dao.ProjectDao;
import domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiProjectController {
    @Autowired
    private ProjectDao projectDao;

    @RequestMapping(value = "/projects/{id}/{manId}", method = RequestMethod.GET)
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<Project> post(@PathVariable("id") final Long id, @PathVariable("manId") final Long manId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Project project = projectDao.findById(id).orElse(null);
        if(project == null){
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(project, headers, HttpStatus.OK);
        }
    }
}

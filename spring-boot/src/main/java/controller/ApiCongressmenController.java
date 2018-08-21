package controller;

import dao.CongressmanDao;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApiCongressmenController {
    @Autowired
    private CongressmanDao congressmanDao;

    @RequestMapping(value = "/congressmen/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Congressman> getCongressman(@PathVariable("name") String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        List<Congressman> congressmen = congressmanDao.findByName(name);
        if(congressmen == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(congressmen.get(0), headers, HttpStatus.OK);
    }
}

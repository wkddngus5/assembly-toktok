package controller;

import domain.User;
import domain.UserCreate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiAdminCommitteeController {

    @RequestMapping(value = "/administrator/committees/{id}", method = RequestMethod.POST)
    public ResponseEntity<UserCreate> addCommittees(@PathVariable Long id, @RequestBody User request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/administrator/committees/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserCreate> updateCommittees(@PathVariable Long id, @RequestBody User request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/administrator/committees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserCreate> deleteCommittees(@PathVariable Long id, @RequestBody User request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

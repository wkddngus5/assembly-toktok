package controller;

import dao.UserDao;
import domain.User;
import domain.UserCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static domain.User.USER_ROLE_CITIZEN;
import static domain.User.USER_ROLE_STAFF;

@RestController
public class ApiAdminManagerController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/administrator/managers", method = RequestMethod.POST)
    public ResponseEntity<UserCreate> addAdministrator(@RequestBody User request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        User addAdministrator = updateAdministrator(request.getEmail(), request.getProvider(), USER_ROLE_STAFF);
        if (addAdministrator == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(User.ResponseUser(addAdministrator), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/administrator/managers", method = RequestMethod.DELETE)
    public ResponseEntity<UserCreate> deleteAdministrator(@RequestBody User request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        User addAdministrator = updateAdministrator(request.getEmail(), request.getProvider(), USER_ROLE_CITIZEN);
        if (addAdministrator == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(User.ResponseUser(addAdministrator), headers, HttpStatus.OK);
        }
    }

    private User updateAdministrator(String email, String provider, String role) {
        userDao.updateUserRole(email, provider, role);
        return userDao.findByEmailAndProvider(email, provider).get(0);
    }

}

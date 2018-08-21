package controller;

import dao.UserDao;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiAdminUserController {

    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "/administrator/add", method = RequestMethod.PUT)
    public ResponseEntity<User> addStaff(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        User dbUser = userDao.findByEmail(user.getEmail());
        if(dbUser == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        dbUser.setRole("staff");
        return  new ResponseEntity<>(userDao.save(dbUser), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/administrator/delete/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> deleteStaff(@PathVariable long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        User dbUser = userDao.findById(id).get();
        if(dbUser == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        dbUser.setRole(null);
        return  new ResponseEntity<>(userDao.save(dbUser), headers, HttpStatus.OK);
    }
}

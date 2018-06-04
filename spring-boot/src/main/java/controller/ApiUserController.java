package controller;

import com.google.gson.Gson;
import domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiUserController {
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED )
    public String post(@RequestBody User user) {
        System.out.println(new Gson().toJson(user));
        return "login";
    }
}

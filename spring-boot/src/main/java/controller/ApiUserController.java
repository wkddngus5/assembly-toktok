package controller;

import dao.UserDao;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static domain.User.USER_PROVIDER_EMAIL;

@RestController
public class ApiUserController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserCreate> post(@RequestBody UserCreate userRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<User> userList = userDao.findByEmailAndProvider(userRequest.getEmail(), USER_PROVIDER_EMAIL);
        if (userList.isEmpty()) {
            User user = userDao.save(User.CreateUser(userRequest, passwordEncoder));
            if (user == null) {
                return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(User.ResponseUser(user), headers, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/users/auth/email", method = RequestMethod.POST)
    public AuthenticationToken login(@RequestBody AuthenticationRequest authenticationRequest, HttpSession session) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        User user = userDao.findByEmail(email);
        return new AuthenticationToken(user.getEmail(), user.getNickname(), user.getImage(), user.getRole(), session.getId());
    }

}

package controller;

import dao.UserDao;
import domain.*;
import error.PasswordTokenNotFoundException;
import error.UserNotFoundException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

import static domain.User.USER_PROVIDER_EMAIL;

@RestController
public class ApiUserController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Configuration configuration;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserCreate> post(@RequestBody UserCreate userRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<User> userList = userDao.findByEmailAndProvider(userRequest.getEmail(), USER_PROVIDER_EMAIL);
        if (userList.isEmpty()) {
            User user = userDao.save(User.CreateUser(userRequest, passwordEncoder));
            if (user == null) {
                return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<>(User.ResponseUser(user), headers, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users/{provider}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserCreate> joinSocial(@PathVariable("provider") String provider, @RequestBody UserCreate request, HttpSession session, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User user = userDao.findByProviderId(provider, request.getUid());
        if (user == null) {
            User joinUser = userDao.save(User.CreateSocialUser(request, provider, passwordEncoder));
            if (joinUser == null) {
                return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(joinUser, null, joinUser.getAuthorities()));
                userDao.updateLoginInformation(joinUser.getId(), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), httpRequest.getRemoteAddr());
                return new ResponseEntity<>(User.ResponseUser(joinUser), headers, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users/nickname", method = RequestMethod.PUT)
    public ResponseEntity<ApiResult> updateNickname(@RequestBody UserCreate request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userDao.updateUserInformation(principal.getId(), request.getNickname(), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        return new ResponseEntity<>(new ApiResult(true, "Update UserInformation"), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/password", method = RequestMethod.PUT)
    public ResponseEntity<ApiResult> updatePassword(@RequestBody UserCreate request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userDao.updateUserPassword(principal.getId(), passwordEncoder.encode(request.getPassword()), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        return new ResponseEntity<>(new ApiResult(true, "Update Password"), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/auth/email", method = RequestMethod.POST)
    public AuthenticationToken login(@RequestBody AuthenticationRequest authenticationRequest, HttpSession session, HttpServletRequest request) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        User user = userDao.findByProviderId(USER_PROVIDER_EMAIL, email);
        userDao.updateLoginInformation(user.getId(), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), request.getRemoteAddr());
        AuthenticationToken authenticationToken = new AuthenticationToken(user.getEmail(), user.getNickname(), user.getImage(), user.getRole(), session.getId());
        return authenticationToken;
    }

    @RequestMapping(value = "/users/password/edit", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> changePassword(@RequestBody PasswordChangeRequest request) throws Exception {
        User user = userDao.getUserByPasswordResetToken(request.getToken());
        if (user == null) {
            throw new PasswordTokenNotFoundException("유효하지 않은 인증 토큰.");
        }
        userDao.changeUserPassword(passwordEncoder.encode(request.getPassword()), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), user.getId());

        return new ResponseEntity<>(new ApiResult(true, "Change Password"), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/password", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> password(@RequestBody UserCreate userRequest) throws Exception {
        User user = userDao.findByEmail(userRequest.getEmail());
        if (user == null) {
            throw new UserNotFoundException("등록되지 않은 이메일 주소입니다.");
        }

        String token = UUID.randomUUID().toString();
        userDao.createPasswordResetTokenForUser(token, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), user.getId());

        Map model = new HashMap();
        model.put("name", user.getNickname());
        model.put("url", "http://toktok.io/users/password/edit?reset_password_token=" + token);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Template template = configuration.getTemplate("reset_password_email_template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setTo(userRequest.getEmail());
        helper.setSubject("비밀번호 재설정");
        helper.setText(html, true);
        helper.setFrom("info@toktok.io");

        javaMailSender.send(message);
        return new ResponseEntity<>(new ApiResult(true, "Send Email"), HttpStatus.OK);
    }
}

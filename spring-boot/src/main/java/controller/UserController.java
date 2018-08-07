package controller;

import dao.ParticipationsDao;
import dao.ProjectDao;
import domain.Participations;
import domain.Project;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ParticipationsDao participationsDao;

    private UserService userService;

    @RequestMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/my")
    public ModelAndView my(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        User sessionedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        modelAndView.setViewName("my");
        modelAndView.addObject("proposals", projectDao.findByUser_id(sessionedUser.getId()));
        List<Project> participations = new ArrayList<>();
        List<Participations> participationsList = participationsDao.findByUserId(sessionedUser.getId());
        for(Participations participation : participationsList) {
            participations.add(projectDao.findById(participation.getProject_id()).get());
        }
        modelAndView.addObject("participations", participations);
        return modelAndView;
    }

    @RequestMapping("/loginTest")
    public ModelAndView loginTest(ModelAndView modelAndView) {
        modelAndView.setViewName("loginTest");
        return modelAndView;
    }

    @RequestMapping("/indexTest")
    public ModelAndView indexTest(ModelAndView modelAndView) {
        modelAndView.setViewName("indexTest");
        return modelAndView;
    }

    @RequestMapping("/users/form")
    public ModelAndView userForm(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("userForm");
        return modelAndView;
    }

    @RequestMapping("/users/password")
    public ModelAndView findPassword(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("findPassword");
        return modelAndView;
    }

    @RequestMapping("/users/form/{provider}")
    public ModelAndView socialLogin(@PathVariable String provider, @RequestParam("uid") String uid, @RequestParam("email") String email, @RequestParam("image") String image, ModelAndView modelAndView) {
        modelAndView.addObject("provider", provider);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("email", email);
        modelAndView.addObject("image", image);

        modelAndView.setViewName("userFormSocial");
        return modelAndView;
    }
}

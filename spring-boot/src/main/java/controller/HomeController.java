package controller;

import dao.MainSlideDao;
import dao.ProjectDao;
import dao.ProposalDao;
import dao.QuestionDao;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private ProposalDao proposalDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private MainSlideDao mainSlideDao;

    @Autowired
    private QuestionDao questionDao;

    @RequestMapping("/")
    public ModelAndView get(ModelAndView modelAndView, Pageable pageable, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        modelAndView.setViewName("index");

        modelAndView.addObject("proposals", proposalDao.findAll());
        modelAndView.addObject("questions", questionDao.findAll());
        modelAndView.addObject("projects_best", projectDao.selectByBest());
        modelAndView.addObject("projects_imminent", projectDao.selectByImminent());
        modelAndView.addObject("projects_new", projectDao.selecteByCreateTime());
        modelAndView.addObject("mainslieds", mainSlideDao.findAll());

        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/users/form")
    public ModelAndView userForm(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("userForm");
        return modelAndView;
    }

    @RequestMapping("/users/password")
    public ModelAndView findPassword(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("findPassword");
        return modelAndView;
    }
}

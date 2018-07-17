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
    public ModelAndView get(ModelAndView modelAndView, Pageable pageable) {
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
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
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
    public ModelAndView userForm(ModelAndView modelAndView) {
        modelAndView.setViewName("userForm");
        return modelAndView;
    }

    @RequestMapping("/users/password")
    public ModelAndView findPassword(ModelAndView modelAndView) {
        modelAndView.setViewName("findPassword");
        return modelAndView;
    }
}

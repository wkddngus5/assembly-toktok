package controller;

import dao.MainSlideDao;
import dao.ProjectDao;
import dao.ProposalDao;
import dao.QuestionDao;
import domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UserService userService = new UserService();

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
        modelAndView = userService.addSessionInfo(modelAndView, session);
        modelAndView.setViewName("index");

        modelAndView.addObject("proposals", proposalDao.findAll());
        modelAndView.addObject("questions", questionDao.findAll());
        List<Project> projects = projectDao.selectByBest();
        for(Project project : projects) {
            project.limitTitle();
        }
        modelAndView.addObject("projects_best", projects);
        projects = projectDao.selectByImminent();
        for(Project project : projects) {
            project.limitTitle();
        }

        modelAndView.addObject("projects_imminent", projects);

        projects = projectDao.selecteByCreateTime();
        for(Project project : projects) {
            project.limitTitle();
        }
        modelAndView.addObject("projects_new", projects);
        modelAndView.addObject("mainslides", mainSlideDao.findAll(new Sort("order")));

        return modelAndView;
    }


//    @RequestMapping("/users/form")
//    public ModelAndView userForm(ModelAndView modelAndView, HttpSession session) {
//        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
//            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        }
//        modelAndView.setViewName("userForm");
//        return modelAndView;
//    }
//
//    @RequestMapping("/users/passwordForm")
//    public ModelAndView editPassword(ModelAndView modelAndView, HttpSession session) {
//        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
//            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        }
//        modelAndView.setViewName("editPassword");
//        return modelAndView;
//    }

    @RequestMapping("/privacy")
    public ModelAndView privacy(ModelAndView modelAndView, HttpSession session) {
        modelAndView = userService.addSessionInfo(modelAndView, session);
        modelAndView.setViewName("privacy");
        return modelAndView;
    }

    @RequestMapping("/userAgreement")
    public ModelAndView userAgreement(ModelAndView modelAndView, HttpSession session) {
        modelAndView = userService.addSessionInfo(modelAndView, session);
        modelAndView.setViewName("userAgreement");
        return modelAndView;
    }
}

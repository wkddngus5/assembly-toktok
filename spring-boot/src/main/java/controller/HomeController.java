package controller;

import dao.MainSlideDao;
import dao.ProjectDao;
import dao.ProposalDao;
import dao.QuestionDao;
import domain.MainSlide;
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
    private static final int MAIN_SLIDE_ORDER_START  = 2;

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

        int order = MAIN_SLIDE_ORDER_START;

        List<MainSlide> slides = mainSlideDao.findAllOrOrderByOrderAsc();
        for(MainSlide slide : slides) {
            slide.setOrder(order++);
        }

        modelAndView.addObject("mainslides", slides);

        return modelAndView;
    }

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

    @RequestMapping("/checking")
    public ModelAndView checking(ModelAndView modelAndView) {
        modelAndView.setViewName("checking");
        return modelAndView;
    }
}

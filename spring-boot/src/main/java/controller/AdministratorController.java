package controller;

import dao.*;
import domain.Congressmen;
import domain.MainSlide;
import domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private CommitteeDao committeeDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private TimelineDao timelineDao;

    @Autowired
    private CongressmenDao congressmenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MainSlideDao mainSlideDao;

    UserService userService = new UserService();

    @RequestMapping("/projects")
    public ModelAndView adminProjects(ModelAndView modelAndView, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);
        modelAndView.setViewName("adminProjects");
        List<Project> projects = projectDao.findAll();
        for(Project project : projects) {
            project.limitTitle();
            project.translateStatus();
        }

        modelAndView.addObject("projects", projects);
        return modelAndView;
    }

    @RequestMapping("/projects/{id}")
    public ModelAndView adminProject(ModelAndView modelAndView, @PathVariable("id") final Long id, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminProject");
        modelAndView.addObject("project", projectDao.findById(id).get());
        return modelAndView;
    }

    @RequestMapping("/projectForm/{id}")
    public ModelAndView adminProjectsForm(ModelAndView modelAndView, @PathVariable("id") final Long id, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminProjectForm");
        return modelAndView;
    }

    @RequestMapping("/projects/{id}/timelines")
    public ModelAndView adminProjectsTimelines(ModelAndView modelAndView, @PathVariable("id") final Long id, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminProjectTimelines");
        modelAndView.addObject("timelines", timelineDao.findByProjectId(id));
        modelAndView.addObject("project", projectDao.findById(id).get());
        return modelAndView;
    }

    @RequestMapping("/committees")
    public ModelAndView adminComittees(ModelAndView modelAndView,  HttpSession session) {
        userService.addSessionInfo(modelAndView, session);
        modelAndView.addObject("committees", committeeDao.findAll());

        modelAndView.setViewName("adminCommittees");
        return modelAndView;
    }

    @RequestMapping("/assemblyman")
    public ModelAndView adminAssemblyman(ModelAndView modelAndView,  HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminAssemblyman");
        modelAndView.addObject("assemblymen", congressmenDao.findAll());
        modelAndView.addObject("committees", committeeDao.findAll());
        return modelAndView;
    }

    @RequestMapping("/slides")
    public ModelAndView adminSlides(ModelAndView modelAndView,  HttpSession session) {
        userService.addSessionInfo(modelAndView, session);
        modelAndView.setViewName("adminSlides");
        modelAndView.addObject("slides", mainSlideDao.findAllOrOrderByOrderAsc());
        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView adminList(ModelAndView modelAndView, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminList");
        modelAndView.addObject("users", userDao.findStaff());
        return modelAndView;
    }
}
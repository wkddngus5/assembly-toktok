package controller;

import dao.ProjectDao;
import dao.TimelineDao;
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
    UserService userService = new UserService();

    @RequestMapping("/projects")
    public ModelAndView adminProjects(ModelAndView modelAndView, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminProjects");
        return modelAndView;
    }

    @RequestMapping("/projects/{id}")
    public ModelAndView adminProject(ModelAndView modelAndView, @PathVariable("id") final Long id, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminProject");
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
        return modelAndView;
    }

    @RequestMapping("/committees")
    public ModelAndView adminComittees(ModelAndView modelAndView,  HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminCommittees");
        return modelAndView;
    }

    @RequestMapping("/assemblyman")
    public ModelAndView adminAssemblyman(ModelAndView modelAndView,  HttpSession session) {
        userService.addSessionInfo(modelAndView, session);

        modelAndView.setViewName("adminAssemblyman");
        return modelAndView;
    }
}
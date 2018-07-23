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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
    @RequestMapping("/projects")
    public ModelAndView adminProjects(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        modelAndView.setViewName("adminProjects");
        return modelAndView;
    }

    @RequestMapping("/projects/{id}")
    public ModelAndView adminProject(ModelAndView modelAndView, @PathVariable("id") final Long id, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        modelAndView.setViewName("adminProject");
        return modelAndView;
    }

    @RequestMapping("/projectForm/{id}")
    public ModelAndView adminProjectsForm(ModelAndView modelAndView, @PathVariable("id") final Long id, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        modelAndView.setViewName("adminProjectForm");
        return modelAndView;
    }
}
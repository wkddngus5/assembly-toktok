package controller;

import dao.ProjectDao;
import domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController {
    @Autowired
    private ProjectDao projectDao;

    @RequestMapping("/projects/{id}")
    public ModelAndView get(ModelAndView modelAndView, @PathVariable("id") final Long id) {
        modelAndView.setViewName("project");
        Project project = projectDao.findById(id).orElse(null);
        modelAndView.addObject("project", project);
        return modelAndView;
    }

    @RequestMapping("/projects")
    public ModelAndView projectList(ModelAndView modelAndView) {
        modelAndView.setViewName("projectList");
        return modelAndView;
    }

    @RequestMapping("/projectForm")
    public ModelAndView projectForm(ModelAndView modelAndView) {
        modelAndView.setViewName("projectForm");
        return modelAndView;
    }

    @RequestMapping("/projects/search")
    public ModelAndView projectSearch(ModelAndView modelAndView) {
        modelAndView.setViewName("projectSearch");
        return modelAndView;
    }
}
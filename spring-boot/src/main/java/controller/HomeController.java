package controller;

import dao.MainSlideDao;
import dao.ProjectDao;
import dao.ProposalDao;
import dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/projects/{id}")
    public ModelAndView project(ModelAndView modelAndView) {
        modelAndView.setViewName("project");
        return modelAndView;
    }
}

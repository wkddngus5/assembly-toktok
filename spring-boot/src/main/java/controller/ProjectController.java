package controller;

import dao.ParticipationsDao;
import dao.ProjectDao;
import dao.TimelineDao;
import domain.Comment;
import domain.Project;
import domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ProjectController {
    private UserService userService = new UserService();

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private TimelineDao timelineDao;
    @Autowired
    private ParticipationsDao participationsDao;

    @RequestMapping("/projects/{id}")
    public ModelAndView get(ModelAndView modelAndView, @PathVariable("id") final Long id, HttpSession session) {
        userService.addSessionInfo(modelAndView, session);
        User sessionedUser = userService.getSessionedUser();

        modelAndView.setViewName("project");
        Project project = projectDao.findById(id).orElse(null);
        modelAndView.addObject("project", project);
        if(sessionedUser != null) {
            modelAndView.addObject("participations",
                    participationsDao.findByUserIdAndProjectId(sessionedUser.getId(), id));
        }

        List<Map<String, Object>> timelineList = timelineDao.findByProjectId(project.getId());
        modelAndView.addObject("timelines", timelineList);
        return modelAndView;
    }

    @RequestMapping("/projects")
    public ModelAndView projectList(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("projectList");
        return modelAndView;
    }

    @RequestMapping("/projectForm")
    public ModelAndView projectForm(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("projectForm");
        return modelAndView;
    }

    @RequestMapping("/projects/search")
    public ModelAndView projectSearch(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("projectSearch");
        return modelAndView;
    }
}

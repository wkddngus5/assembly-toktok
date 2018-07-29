package controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @RequestMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/my")
    public ModelAndView my(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("my");
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
    public ModelAndView userForm(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("userForm");
        return modelAndView;
    }

    @RequestMapping("/users/password")
    public ModelAndView findPassword(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        modelAndView.setViewName("findPassword");
        return modelAndView;
    }

    @RequestMapping("/users/form/{provider}")
    public ModelAndView socialLogin(@PathVariable String provider, @RequestParam("uid") String uid, @RequestParam("email") String email, ModelAndView modelAndView) {
        modelAndView.addObject("provider", provider);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("email", email);

        modelAndView.setViewName("userFormSocial");
        return modelAndView;
    }
}

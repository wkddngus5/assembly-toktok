package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
    @GetMapping("/testWeb")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("body", "value");
        String[] users = {"apple", "banana1", "circ11le", "고래", "다커"};
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/aws")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("aws");
        return modelAndView;
    }

    @GetMapping("/aws/test")
    public ModelAndView testAws() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("awsTest");
        return modelAndView;
    }

    @GetMapping("/aws/test2")
    public ModelAndView testAws2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index2");
        return modelAndView;
    }
}

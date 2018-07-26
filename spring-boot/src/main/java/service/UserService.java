package service;

import dao.UserDao;
import domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Service
public class UserService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email);
    }

    public ModelAndView addSessionInfo(ModelAndView modelAndView, HttpSession session) {
        if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            User sessionedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            modelAndView.addObject("authenticatedUser", sessionedUser);
            if(sessionedUser.getRole().toString().equals("staff")) {
                log.info("staff logined");
                modelAndView.addObject("staff", sessionedUser.getRole());
            }
        }
        return modelAndView;
    }
}

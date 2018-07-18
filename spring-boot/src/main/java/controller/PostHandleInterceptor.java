package controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 로그인처리를 담당하는 인터셉터
public class PostHandleInterceptor extends HandlerInterceptorAdapter {

    // preHandle() : 컨트롤러보다 먼저 수행되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    // 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(modelAndView == null) modelAndView = new ModelAndView();
        if(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            modelAndView.addObject("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        super.postHandle(request, response, handler, modelAndView);
    }

}

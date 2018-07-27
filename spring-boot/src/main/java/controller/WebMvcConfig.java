package controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PreHandleInterceptor())
                .addPathPatterns("/administrator/**")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/users/**")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/resources/**")
        ;

        registry.addInterceptor(new PostHandleInterceptor())
                .addPathPatterns("/*");
    }
}

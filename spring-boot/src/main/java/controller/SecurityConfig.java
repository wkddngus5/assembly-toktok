package controller;

import component.RestAuthenticationEntryPoint;
import component.RestLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    RestLogoutSuccessHandler restLogoutSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/script/**", "/img/**", "/fonts/**", "lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("staff")
                .antMatchers("/", "/index", "/users/**", "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/projects/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .permitAll()
                .invalidateHttpSession(true)
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

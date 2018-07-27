package controller;

import component.OAuth2SuccessHandler;
import component.RestAuthenticationEntryPoint;
import component.RestLogoutSuccessHandler;
import domain.enums.SocialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import service.UserService;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static domain.User.USER_ROLE_CITIZEN;
import static domain.User.USER_ROLE_STAFF;

@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    RestLogoutSuccessHandler restLogoutSuccessHandler;
    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

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
                .antMatchers("/administrator/**").hasRole(USER_ROLE_STAFF)
                .antMatchers("/connect/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/users").hasAnyRole(USER_ROLE_STAFF, USER_ROLE_CITIZEN)
                .antMatchers("/", "/index", "/users/**", "/login", "/loginTest").permitAll()
                .antMatchers(HttpMethod.GET, "/projects/**").permitAll()
                .antMatchers("/aws/**").permitAll()
                .antMatchers("/api/aws/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .permitAll()
                .invalidateHttpSession(true)
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/users/auth/facebook");
        facebookFilter.setRestTemplate(new OAuth2RestTemplate(facebook(), oAuth2ClientContext));
        facebookFilter.setTokenServices(new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
        facebookFilter.setAuthenticationSuccessHandler(new OAuth2SuccessHandler(SocialType.FACEBOOK, userService));
        facebookFilter.setAuthenticationFailureHandler((request, response, exception) -> response.sendRedirect("/"));
        return facebookFilter;
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

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("twitter.client")
    public AuthorizationCodeResourceDetails twitter() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("twitter.resource")
    public ResourceServerProperties twitterResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}

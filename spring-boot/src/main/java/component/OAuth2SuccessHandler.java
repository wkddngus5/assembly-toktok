package component;

import domain.User;
import domain.enums.SocialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import service.S3Wrapper;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    final SocialType provider;
    final UserService userService;
    private S3Wrapper s3Wrapper;

    public OAuth2SuccessHandler(SocialType provider, UserService userService, S3Wrapper s3Wrapper) {
        this.provider = provider;
        this.userService = userService;
        this.s3Wrapper = s3Wrapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User account = userService.loadUserByProviderId(provider.getValue(), authentication.getName());

        if (account != null) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities()));
            response.sendRedirect("/");
        } else {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            Map<String, String> map = (HashMap<String, String>) oAuth2Authentication.getUserAuthentication().getDetails();
            String imageFileName = s3Wrapper.uploadImageUrl(provider.getProfileImage(authentication.getName()), provider.getValue() + "_" + authentication.getName() + ".jpg");

            response.sendRedirect("/users/form/" + provider.getValue() + "?uid=" + authentication.getName() + "&email=" + map.get("email") + "&image=" + imageFileName);
        }
    }
}

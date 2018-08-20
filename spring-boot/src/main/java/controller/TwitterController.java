package controller;

import domain.User;
import domain.enums.SocialType;
import domain.twitter.TwitterResponse;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import service.S3Wrapper;
import service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TwitterController {
    private static String clientId;
    private static String clientSecret;
    private static String callbackUri;

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private S3Wrapper s3Wrapper;

    @PostConstruct
    private void init() {
        clientId = environment.getProperty("twitter.clientId");
        clientSecret = environment.getProperty("twitter.clientSecret");
        callbackUri = environment.getProperty("twitter.client.redirectUrl");
    }

    @GetMapping(value = "/users/auth/twitter")
    public void twitter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth1Operations operations = new TwitterConnectionFactory(clientId, clientSecret).getOAuthOperations();
        OAuthToken oAuthToken = operations.fetchRequestToken(callbackUri, null);
        String authenticationUrl = operations.buildAuthenticateUrl(oAuthToken.getValue(), new OAuth1Parameters());

        request.getServletContext().setAttribute("token", oAuthToken);
        response.sendRedirect(authenticationUrl);
    }

    @GetMapping(value = "/users/auth/twitter/authorize")
    public String twitterComplete(HttpServletRequest request, @RequestParam(name = "oauth_token", required = false) String oauthToken, @RequestParam(name = "oauth_verifier", required = false) String oauthVerifier, @RequestParam(name = "denied", required = false) String denied) throws IOException {
        if (denied != null) {
            return "redirect:/";
        }
        Connection<Twitter> connection = getAccessTokenToConnection(request, oauthVerifier);
        User account = userService.loadUserByProviderId(SocialType.TWITTER.getValue(), connection.getKey().getProviderUserId());
        if (account != null) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities()));
            return "redirect:/";
        } else {
            String email = getEmailAddress(connection.createData().getAccessToken(), connection.createData().getSecret());
            String imageFileName = s3Wrapper.uploadProfileImage(connection.getProfileUrl(), SocialType.TWITTER.getValue() + "_" + connection.getKey().getProviderUserId() + ".jpg");

            return "redirect:/users/form/" + SocialType.TWITTER.getValue() + "?uid=" + connection.getKey().getProviderUserId() + "&email=" + email + "&image=" + imageFileName;
        }
    }

    private String getEmailAddress(String accessToken, String secret) {
        TwitterTemplate twitterTemplate = new TwitterTemplate(clientId, clientSecret, accessToken, secret);
        RestTemplate restTemplate = twitterTemplate.getRestTemplate();

        TwitterResponse response = restTemplate.getForObject("https://api.twitter.com/1.1/account/verify_credentials.json?include_email=true", TwitterResponse.class);
        return response.getEmail();
    }

    private Connection<Twitter> getAccessTokenToConnection(HttpServletRequest request, @RequestParam(name = "oauth_verifier") String oauthVerifier) {
        TwitterConnectionFactory twitterConnectionFactory = new TwitterConnectionFactory(clientId, clientSecret);
        OAuth1Operations operations = twitterConnectionFactory.getOAuthOperations();
        OAuthToken requestToken = (OAuthToken)request.getServletContext().getAttribute("token");
        request.getServletContext().removeAttribute("token");
        OAuthToken accessToken = operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);

        return twitterConnectionFactory.createConnection(accessToken);
    }
}

package controller;

import domain.User;
import domain.enums.SocialType;
import domain.kakao.KakaoAccessTokenResponse;
import domain.kakao.KakaoAccountMeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import service.S3Wrapper;
import service.UserService;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Controller
public class KakaoController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private S3Wrapper s3Wrapper;

    private static String clientId;
    private static String accessTokenUri;
    private static String userAuthorizationUri;
    private static String redirectUrl;
    private static String userInfoUri;

    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        clientId = environment.getProperty("kakao.client.clientId");
        userAuthorizationUri = environment.getProperty("kakao.client.userAuthorizationUri");
        accessTokenUri = environment.getProperty("kakao.client.accessTokenUri");
        redirectUrl = environment.getProperty("kakao.client.redirectUrl");
        userInfoUri = environment.getProperty("kakao.resource.userInfoUri");

        restTemplate = new RestTemplate();
    }

    @GetMapping(value = "/users/auth/kakao")
    public String authStart() {
        return "redirect:" + userAuthorizationUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUrl + "&response_type=code";
    }

    @RequestMapping("/users/auth/kakao/authorize")
    public String authKakao(@RequestParam("code") String code) throws IOException {
        ResponseEntity<KakaoAccessTokenResponse> accessTokenResponse = restTemplate.exchange(accessTokenUri, HttpMethod.POST, new HttpEntity<>(getAccessTokenParameters(code), getAccessTokenHeader()), KakaoAccessTokenResponse.class);
        ResponseEntity<KakaoAccountMeResponse> userMeResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, new HttpEntity<>(getUserMeHeader(accessTokenResponse.getBody().getTokenType() + " " + accessTokenResponse.getBody().getAccessToken())), KakaoAccountMeResponse.class);

        User account = userService.loadUserByProviderId(SocialType.KAKAO.getValue(), userMeResponse.getBody().getId().toString());
        if (account != null) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities()));
            return "redirect:/";
        } else {
            String imageFileName = s3Wrapper.uploadProfileImage(userMeResponse.getBody().getProperties().getProfileImage(), SocialType.KAKAO.getValue() + "_" + userMeResponse.getBody().getId() + ".jpg");

            return "redirect:/users/form/" + SocialType.KAKAO.getValue() + "?uid=" + userMeResponse.getBody().getId() + "&email=&image=" + imageFileName;
        }
    }

    private HttpHeaders getUserMeHeader(String authorization) {
        HttpHeaders headers = getAccessTokenHeader();
        headers.add("Authorization", authorization);
        return headers;
    }

    private HttpHeaders getAccessTokenHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

    private MultiValueMap<String, String> getAccessTokenParameters(String code) {
        MultiValueMap<String, String> additionalParameters = new LinkedMultiValueMap<>();
        additionalParameters.add("grant_type", "authorization_code");
        additionalParameters.add("client_id", clientId);
        additionalParameters.add("redirect_uri", redirectUrl);
        additionalParameters.add("code", code);

        return additionalParameters;
    }
}

package domain.enums;

/**
 * Created by KimYJ on 2017-05-18.
 */
public enum SocialType {
    FACEBOOK("facebook"),
    TWITTER("twitter"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String ROLE_PREFIX = "ROLE_";
    private String name;

    SocialType(String name) {
        this.name = name;
    }

    public String getRoleType() { return ROLE_PREFIX + name.toUpperCase(); }

    public String getValue() { return name; }

    public boolean isEquals(String authority) {
        return this.getRoleType().equals(authority);
    }

    public String getProfileImage(String id) {
        if (name.equals(FACEBOOK.getValue())) {
            return "https://graph.facebook.com/" + id + "/picture?type=large";
        }
        return "";
    }
}

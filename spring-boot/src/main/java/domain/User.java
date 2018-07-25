package domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements UserDetails {
    public static String ROLE_PREFIX = "ROLE_";
    public static String USER_ROLE_CITIZEN = "citizen";
    public static String USER_ROLE_STAFF = "staff";

    public static String USER_PROVIDER_EMAIL = "email";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String remember_created_at;
    private int sign_in_count;
    private String current_sign_in_at;
    private String last_sign_in_at;
    private String current_sign_in_ip;
    private String last_sign_in_ip;
    private String provider;
    private String uid;
    private String nickname;
    private String image;
    private String created_at;
    private String updated_at;
    private String role;
    private String encrypted_password;
    private String reset_password_token;
    private String reset_password_sent_at;
    private String confirmation_token;
    private String confirmed_at;
    private String confirmation_sent_at;
    private String unconfirmed_email;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemember_created_at() {
        return remember_created_at;
    }

    public void setRemember_created_at(String remember_created_at) {
        this.remember_created_at = remember_created_at;
    }

    public int getSign_in_count() {
        return sign_in_count;
    }

    public void setSign_in_count(int sign_in_count) {
        this.sign_in_count = sign_in_count;
    }

    public String getCurrent_sign_in_at() {
        return current_sign_in_at;
    }

    public void setCurrent_sign_in_at(String current_sign_in_at) {
        this.current_sign_in_at = current_sign_in_at;
    }

    public String getLast_sign_in_at() {
        return last_sign_in_at;
    }

    public void setLast_sign_in_at(String last_sign_in_at) {
        this.last_sign_in_at = last_sign_in_at;
    }

    public String getCurrent_sign_in_ip() {
        return current_sign_in_ip;
    }

    public void setCurrent_sign_in_ip(String current_sign_in_ip) {
        this.current_sign_in_ip = current_sign_in_ip;
    }

    public String getLast_sign_in_ip() {
        return last_sign_in_ip;
    }

    public void setLast_sign_in_ip(String last_sign_in_ip) {
        this.last_sign_in_ip = last_sign_in_ip;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public String getReset_password_token() {
        return reset_password_token;
    }

    public void setReset_password_token(String reset_password_token) {
        this.reset_password_token = reset_password_token;
    }

    public String getReset_password_sent_at() {
        return reset_password_sent_at;
    }

    public void setReset_password_sent_at(String reset_password_sent_at) {
        this.reset_password_sent_at = reset_password_sent_at;
    }

    public String getConfirmation_token() {
        return confirmation_token;
    }

    public void setConfirmation_token(String confirmation_token) {
        this.confirmation_token = confirmation_token;
    }

    public String getConfirmed_at() {
        return confirmed_at;
    }

    public void setConfirmed_at(String confirmed_at) {
        this.confirmed_at = confirmed_at;
    }

    public String getConfirmation_sent_at() {
        return confirmation_sent_at;
    }

    public void setConfirmation_sent_at(String confirmation_sent_at) {
        this.confirmation_sent_at = confirmation_sent_at;
    }

    public String getUnconfirmed_email() {
        return unconfirmed_email;
    }

    public void setUnconfirmed_email(String unconfirmed_email) {
        this.unconfirmed_email = unconfirmed_email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        } else {
            authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + USER_ROLE_CITIZEN));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return encrypted_password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserCreate ResponseUser(User user) {
        UserCreate userCreate = new UserCreate();
        userCreate.setId(user.getId());
        userCreate.setEmail(user.getEmail());
        userCreate.setNickname(user.nickname);
        userCreate.setProfile_img(user.getImage());
        userCreate.setRole(user.getRole());

        return userCreate;
    }

    public static User CreateUser(UserCreate userRequest, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUid(userRequest.getEmail());
        user.setEncrypted_password(passwordEncoder.encode(userRequest.getPassword()));
        user.setNickname(userRequest.getNickname());
        user.setRole(USER_ROLE_CITIZEN);
        user.setProvider(USER_PROVIDER_EMAIL);

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        user.setCreated_at(createDate);
        user.setUpdated_at(createDate);

        return user;
    }

    public static User CreateSocialUser(UserCreate request, String provider, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUid(request.getUid());
        user.setNickname(request.getNickname());
        user.setRole(USER_ROLE_CITIZEN);
        user.setProvider(provider);
        user.setImage(request.getImage());
        user.setEncrypted_password(passwordEncoder.encode(request.getUid()));

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        user.setCreated_at(createDate);
        user.setUpdated_at(createDate);

        return user;
    }

}

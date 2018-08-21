package domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import utils.ImageUploadUtil;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "congressmen")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Congressman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String party;
    private Long committee_id;
    private String created_at;
    private String updated_at;
    private String image;
    private String facebook_url;
    private String twitter_url;
    private String email;
    private String homepage_url;
    @Transient
    private String profile;
    @Transient
    private Long committee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Long getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(Long committee_id) {
        this.committee_id = committee_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFacebook_url() {
        return facebook_url;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public String getTwitter_url() {
        return twitter_url;
    }

    public void setTwitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage_url() {
        return homepage_url;
    }

    public void setHomepage_url(String homepage_url) {
        this.homepage_url = homepage_url;
    }

    public Long getCommittee() {
        return committee;
    }

    public void setCommittee(Long committee) {
        this.committee = committee;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public static Congressman createCommittee(Congressman congressman) {
        Congressman createCongressman = new Congressman();
        createCongressman.setId(congressman.getId());
        createCongressman.setName(congressman.getName());
        createCongressman.setDescription(congressman.getDescription());
        createCongressman.setParty(congressman.getParty());
        createCongressman.setCommittee_id(congressman.getCommittee());
        createCongressman.setImage(congressman.getProfile());
        createCongressman.setFacebook_url(congressman.getFacebook_url());
        createCongressman.setTwitter_url(congressman.getTwitter_url());
        createCongressman.setEmail(congressman.getEmail());
        createCongressman.setHomepage_url(congressman.getHomepage_url());

        createCongressman.setCreated_at(congressman.getCreated_at());
        createCongressman.setUpdated_at(congressman.getUpdated_at());

        return createCongressman;
    }

    public void updateCongressmen(Congressman request) {
        if (request.getName() != null) {
            this.name = request.getName();
        }

        if (request.getDescription() != null) {
            this.description = request.getDescription();
        }

        if (request.getParty() != null) {
            this.party = request.getParty();
        }

        if (request.committee != null) {
            this.committee_id = request.committee;
        }

        if (request.getProfile() != null) {
            this.image = request.getProfile();
        } else {
            this.image = ImageUploadUtil.replaceImagePath(Congressman.class.getSimpleName(), String.valueOf(id), image);
        }

        if (request.getFacebook_url() != null) {
            this.facebook_url = request.getFacebook_url();
        }

        if (request.getTwitter_url() != null) {
            this.twitter_url = request.getTwitter_url();
        }

        if (request.getEmail() != null) {
            this.email = request.getEmail();
        }

        if (request.getHomepage_url() != null) {
            this.homepage_url = request.getHomepage_url();
        }
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }
}

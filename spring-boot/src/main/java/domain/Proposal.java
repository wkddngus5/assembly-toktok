package domain;

import javax.persistence.*;

@Entity
@Table(name = "proposals")
public class Proposal {
    @Id
    @GeneratedValue
    private long id;
    private Long user_id;
    private String title;
    private String body;
    private String created_at;
    private String updated_at;
    private String proposer_name;
    private String proposer_email;
    private String proposer_phone;
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getProposer_name() {
        return proposer_name;
    }

    public void setProposer_name(String proposer_name) {
        this.proposer_name = proposer_name;
    }

    public String getProposer_email() {
        return proposer_email;
    }

    public void setProposer_email(String proposer_email) {
        this.proposer_email = proposer_email;
    }

    public String getProposer_phone() {
        return proposer_phone;
    }

    public void setProposer_phone(String proposer_phone) {
        this.proposer_phone = proposer_phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

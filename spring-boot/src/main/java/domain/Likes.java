package domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long user_id;
    private Long likable_id;
    private String created_at;
    private String updated_at;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Likes() {
    }

    public Likes(Long user_id, Long likable_id, String created_at, String updated_at) {
        this.user_id = user_id;
        this.likable_id = likable_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getLikable_id() {
        return likable_id;
    }

    public void setLikable_id(Long likable_id) {
        this.likable_id = likable_id;
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
}

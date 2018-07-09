package domain;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timelines")
public class Timeline {
    @Id
    @GeneratedValue
    private Integer id;
    private String actor;
    private String image;
    private String body;
    private Integer project_id;
    @Nullable
    private Integer congressman_id;
    private String timeline_date;
    private String created_at;
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getCongressman_id() {
        return congressman_id;
    }

    public void setCongressman_id(Integer congressman_id) {
        this.congressman_id = congressman_id;
    }

    public String getTimeline_date() {
        return timeline_date;
    }

    public void setTimeline_date(String timeline_date) {
        this.timeline_date = timeline_date;
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

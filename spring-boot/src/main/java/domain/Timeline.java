package domain;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "timelines")
public class Timeline {
    @Id
    @GeneratedValue
    private Integer id;
    private String actor;
    private String image;
    private String body;
    private Long project_id;
    @Nullable
    private Long congressman_id;
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

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getCongressman_id() {
        return congressman_id;
    }

    public void setCongressman_id(Long congressman_id) {
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

    public static Timeline createTimeLine(String actor, String image, String body, Long projectId, Long congressmanId, String timeLineDate) {
        Timeline timeline = new Timeline();
        timeline.setActor(actor);
        timeline.setImage(image);
        timeline.setBody(body);
        timeline.setProject_id(projectId);
        timeline.setCongressman_id(congressmanId);
        timeline.setTimeline_date(timeLineDate);

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        timeline.setCreated_at(createDate);
        timeline.setUpdated_at(createDate);

        return timeline;
    }
}

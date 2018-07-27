package domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "timelines")
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String actor;
    private String image;
    private String body;
    private String subject;
    private Long project_id;
    @Nullable
    private Long congressman_id;
    private String timeline_date;
    private String created_at;
    private String updated_at;

    @Transient
    private String contents;
    @Transient
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public static Timeline createTimeline(String actor, String image, String body, String subject, Long projectId, String timeLineDate) {
        Timeline timeline = new Timeline();
        timeline.setActor(actor);
        timeline.setImage(image);
        timeline.setBody(body);
        timeline.setSubject(subject);
        timeline.setProject_id(projectId);
        timeline.setTimeline_date(timeLineDate);

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        timeline.setCreated_at(createDate);
        timeline.setUpdated_at(createDate);

        return timeline;
    }

    public void updateTimeline(Timeline timeline) {
        image = timeline.getImage();
        body = timeline.getContents();
        subject = timeline.getSubject();
        timeline_date = timeline.getDate();
        updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    public static Timeline createTimeline(Timeline timeline) {
        Timeline createTimeline = new Timeline();
        createTimeline.setId(timeline.getId());
        createTimeline.setActor(timeline.getActor());
        createTimeline.setImage(timeline.getImage());
        createTimeline.setBody(timeline.getBody());
        createTimeline.setSubject(timeline.getSubject());
        createTimeline.setProject_id(timeline.getProject_id());
        createTimeline.setTimeline_date(timeline.getDate());
        createTimeline.setCreated_at(timeline.getCreated_at());
        createTimeline.setUpdated_at(timeline.getUpdated_at());

        return createTimeline;
    }
}

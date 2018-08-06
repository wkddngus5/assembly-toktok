package domain;

import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "participations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long user_id;
    private long project_id;
    private String created_at;
    private String updated_at;

    public Participations(long user_id, long project_id) {
        String nowDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        this.user_id = user_id;
        this.project_id = project_id;
        this.created_at = nowDate;
        this.updated_at = nowDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
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

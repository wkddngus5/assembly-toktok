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
}

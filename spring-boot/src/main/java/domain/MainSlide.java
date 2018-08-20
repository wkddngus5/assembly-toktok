package domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "mainslides")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainSlide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order")
    private long order;
    private String image;
    private String url;
    private String created_at;
    private String updated_at;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateSlide(MainSlide slide) {
        if (slide.order != 0) {
            this.order = slide.order;
        }

        if (slide.image != null) {
            this.image = slide.image;
        }

        if (slide.url != null) {
            this.url = slide.url;
        }
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    @Override
    public String toString() {
        return "MainSlide{" +
                "id=" + id +
                ", order=" + order +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

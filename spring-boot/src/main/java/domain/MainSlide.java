package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "mainslides")
public class MainSlide {
    @Id
    @GeneratedValue
    private long id;
    private long order;
    private String image;
    private String url;
    private String created_at;
    private String updated_at;
    private String description;

    public MainSlide() {
    }

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

    public MainSlide(long order, String image, String url, String created_at, String updated_at, String description) {
        this.order = order;
        this.image = image;
        this.url = url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.description = description;
    }

    public void updateSlide(MainSlide slide) {
        this.order = slide.order;
        this.image = slide.image;
        this.url = slide.url;
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

package domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "mainslides")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int order;
    private String url;
    private String created_at;
    private String updated_at;
    private String image;

    public Slide(int order) {
        this.order = order;
    }

    public Slide(int order, String url, String created_at, String updated_at, String description, String image) {
        this.order = order;
        this.url = url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

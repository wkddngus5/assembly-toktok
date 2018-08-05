package domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "committees")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Committee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String created_at;
    private String updated_at;

    @Transient
    private String errorMessage;
    @Transient
    private int congressmanCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCongressmanCount() {
        return congressmanCount;
    }

    public void setCongressmanCount(int congressmanCount) {
        this.congressmanCount = congressmanCount;
    }

    public void updateCommittee(Committee request) {
        name = request.getName();
        description = request.getDescription();
        updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    public static Committee createCommittee(Committee committee) {
        Committee createCommittee = new Committee();
        createCommittee.setId(committee.getId());
        createCommittee.setName(committee.getName());
        createCommittee.setDescription(committee.getDescription());
        createCommittee.setCreated_at(committee.getCreated_at());
        createCommittee.setUpdated_at(committee.getUpdated_at());

        return createCommittee;
    }

    public static Committee createError(String errorMessage, int count) {
        Committee errorCommittee = new Committee();
        errorCommittee.setErrorMessage(errorMessage);
        errorCommittee.setCongressmanCount(count);
        return errorCommittee;
    }

}

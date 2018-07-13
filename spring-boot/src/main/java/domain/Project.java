package domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "projects")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String body;
    private Long proposal_id;
    private Long user_id;
    private String created_at;
    private String updated_at;
    private Long participations_count;
    private Long participations_goal_count;
    private String image;
    private String summary;
    private String proposer_description;
    private String deleted_at;
    private String matching_staff_message;
    private String proposer;
    private String running_platform_url;
    private String proposer_email;
    private String proposer_phone;
    private String running_staff_message;
    private Long primary_committee_id;
    private String status;
    private String fail_staff_message;
    @Column(length = 20)
    private String category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getProposal_id() {
        return proposal_id;
    }

    public void setProposal_id(Long proposal_id) {
        this.proposal_id = proposal_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public Long getParticipations_count() {
        return participations_count;
    }

    public void setParticipations_count(Long participations_count) {
        this.participations_count = participations_count;
    }

    public Long getParticipations_goal_count() {
        return participations_goal_count;
    }

    public void setParticipations_goal_count(Long participations_goal_count) {
        this.participations_goal_count = participations_goal_count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getProposer_description() {
        return proposer_description;
    }

    public void setProposer_description(String proposer_description) {
        this.proposer_description = proposer_description;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getMatching_staff_message() {
        return matching_staff_message;
    }

    public void setMatching_staff_message(String matching_staff_message) {
        this.matching_staff_message = matching_staff_message;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getRunning_platform_url() {
        return running_platform_url;
    }

    public void setRunning_platform_url(String running_platform_url) {
        this.running_platform_url = running_platform_url;
    }

    public String getProposer_email() {
        return proposer_email;
    }

    public void setProposer_email(String proposer_email) {
        this.proposer_email = proposer_email;
    }

    public String getProposer_phone() {
        return proposer_phone;
    }

    public void setProposer_phone(String proposer_phone) {
        this.proposer_phone = proposer_phone;
    }

    public String getRunning_staff_message() {
        return running_staff_message;
    }

    public void setRunning_staff_message(String running_staff_message) {
        this.running_staff_message = running_staff_message;
    }

    public Long getPrimary_committee_id() {
        return primary_committee_id;
    }

    public void setPrimary_committee_id(Long primary_committee_id) {
        this.primary_committee_id = primary_committee_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFail_staff_message() {
        return fail_staff_message;
    }

    public void setFail_staff_message(String fail_staff_message) {
        this.fail_staff_message = fail_staff_message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

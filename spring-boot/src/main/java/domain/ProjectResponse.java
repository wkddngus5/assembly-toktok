package domain;

public class ProjectResponse {
    private String url;
    private String error_msg;

    public ProjectResponse(Long id) {
        this.url = "projects/preview/" + id;
    }

    public ProjectResponse(String message) {
        this.error_msg = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}

package domain;

public class ProjectJoinResponse {
    private String status;
    private String error_msg;

    public ProjectJoinResponse(String status, String message) {
        this.status = status;
        this.error_msg = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}

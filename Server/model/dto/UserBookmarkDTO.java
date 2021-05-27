package Server.model.dto;

public class UserBookmarkDTO {
    private String userEmail;
    private String univId;

    public UserBookmarkDTO(String userEmail, String univId) {
        this.userEmail = userEmail;
        this.univId = univId;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUnivId() {
        return this.univId;
    }

    public void setUnivId(String univId) {
        this.univId = univId;
    }
}

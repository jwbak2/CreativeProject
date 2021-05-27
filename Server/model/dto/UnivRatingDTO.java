package Server.model.dto;

import java.sql.Date;

public class UnivRatingDTO {
    private String userEmail;
    private String univId;
    private String content;
    private Long score;
    private java.sql.Date creationDate;

    public UnivRatingDTO(String userEmail, String univId, String content, Long score, Date creationDate) {
        this.userEmail = userEmail;
        this.univId = univId;
        this.content = content;
        this.score = score;
        this.creationDate = creationDate;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getScore() {
        return this.score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public java.sql.Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(java.sql.Date creationDate) {
        this.creationDate = creationDate;
    }
}

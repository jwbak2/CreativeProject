package Server.model.dto;

import java.sql.Date;

public class DepartmentRatingDTO {
    private String userEmail;
    private String departmentId;
    private String content;
    private Long score;
    private java.sql.Date creationDate;

    public DepartmentRatingDTO(String userEmail, String departmentId, String content, Long score, Date creationDate) {
        this.userEmail = userEmail;
        this.departmentId = departmentId;
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

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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

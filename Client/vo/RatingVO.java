package Client.vo;

import java.sql.Date;

public class RatingVO {
    private String univName;
    private String deptName;
    private String userEmail;
    private String content;
    private int score;
    private java.sql.Date creationDate;

    public RatingVO(String univName, String userEmail, String content, int score, Date creationDate) {
        this.univName = univName;
        this.userEmail = userEmail;
        this.content = content;
        this.score = score;
        this.creationDate = creationDate;
        this.deptName = null;
    }

    public RatingVO(String univName, String deptName, String userEmail, String content, int score, Date creationDate) {
        this.univName = univName;
        this.deptName = deptName;
        this.userEmail = userEmail;
        this.content = content;
        this.score = score;
        this.creationDate = creationDate;
    }

    public void setUnivName(String univName) {
        this.univName = univName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

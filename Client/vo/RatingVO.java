package Client.vo;

import java.io.Serializable;
import java.sql.Date;

public class RatingVO implements Serializable {
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


    public String getUnivName() {
        return univName;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    public Date getCreationDate() {
        return creationDate;
    }

}

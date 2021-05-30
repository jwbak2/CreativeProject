package Client.vo;

import java.io.Serializable;

public class LoginReqVO implements Serializable {

    private String id;
    private String pw;

    public LoginReqVO(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getID() {
        return id;
    }

    public String getPW() {
        return pw;
    }

}

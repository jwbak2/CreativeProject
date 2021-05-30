package Client.vo;

public class LoginReqVO {

    private String id;
    private String pw;

    public LoginReqVO(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

}

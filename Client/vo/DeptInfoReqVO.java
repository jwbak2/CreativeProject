package Client.vo;

import java.io.Serializable;

public class DeptInfoReqVO implements Serializable {

    private String univName;
    private String deptName;

    public DeptInfoReqVO(String univName, String deptName) {
        this.univName = univName;
        this.deptName = deptName;
    }

    public String getUnivName() {
        return univName;
    }

    public String getDeptName() {
        return deptName;
    }

}

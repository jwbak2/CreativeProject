package DTO;

public class DepartmentDTO {
    private String departmentId;
    private String univId;
    private String departmentName;
    private String departmentCls;
    private String dayNightCls;
    private String courseCls;

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getUnivId() {
        return this.univId;
    }

    public void setUnivId(String univId) {
        this.univId = univId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCls() {
        return this.departmentCls;
    }

    public void setDepartmentCls(String departmentCls) {
        this.departmentCls = departmentCls;
    }

    public String getDayNightCls() {
        return this.dayNightCls;
    }

    public void setDayNightCls(String dayNightCls) {
        this.dayNightCls = dayNightCls;
    }

    public String getCourseCls() {
        return this.courseCls;
    }

    public void setCourseCls(String courseCls) {
        this.courseCls = courseCls;
    }
}
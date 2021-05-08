package model.dto;

public class UserDTO {
    private String userEmail;
    private String userPassword;
    private String userName;
    private Long userPhoneNumber;
    private String userArea;
    private Long residentRegistrationNumber;
    private String userCategory;
    private String affiliatedSchool;
    private String affiliatedDepartment;

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserPhoneNumber() {
        return this.userPhoneNumber;
    }

    public void setUserPhoneNumber(Long userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserArea() {
        return this.userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    public Long getResidentRegistrationNumber() {
        return this.residentRegistrationNumber;
    }

    public void setResidentRegistrationNumber(Long residentRegistrationNumber) {
        this.residentRegistrationNumber = residentRegistrationNumber;
    }

    public String getUserCategory() {
        return this.userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getAffiliatedSchool() {
        return this.affiliatedSchool;
    }

    public void setAffiliatedSchool(String affiliatedSchool) {
        this.affiliatedSchool = affiliatedSchool;
    }

    public String getAffiliatedDepartment() {
        return this.affiliatedDepartment;
    }

    public void setAffiliatedDepartment(String affiliatedDepartment) {
        this.affiliatedDepartment = affiliatedDepartment;
    }
}

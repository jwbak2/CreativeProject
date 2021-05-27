package Server.model.dto;

public class DepartmentDetailDTO {
    private Long year;
    private String departmentId;
    private Long admissionFee;
    private Long tuition;
    private Long maleGr;
    private Long femaleGr;
    private Long enteringDomCmntyColl;
    private Long enteringOverseasCmntyColl;
    private Long enteringDomUniv;
    private Long enteringOverseasUniv;
    private Long enteringDomGrSchool;
    private Long enteringOverseasGrSchool;
    private Long domScholarNumber;
    private Long overseasScholarNumber;
    private Long maleEmploymentTarget;
    private Long femaleEmploymentTarget;
    private Long maleDomEmployee;
    private Long femaleDomEmployee;
    private Long maleOverseasEmployee;
    private Long femaleOverseasEmployee;
    private Long enteringRate;
    private Long outSchoolScholarship;
    private Long inSchoolScholarship;
    private Long scholarshipPerPerson;
    private Long numOfFulltimeProfessor;
    private Long thesisResultPerProfessor;
    private Long rearchCostPerProfessor;
    private Long employmentRate;

    public DepartmentDetailDTO(Long year, String departmentId, Long admissionFee, Long tuition, Long maleGr,
                               Long femaleGr, Long enteringDomCmntyColl, Long enteringOverseasCmntyColl, Long enteringDomUniv,
                               Long enteringOverseasUniv, Long enteringDomGrSchool, Long enteringOverseasGrSchool,
                               Long domScholarNumber, Long overseasScholarNumber, Long maleEmploymentTarget,
                               Long femaleEmploymentTarget, Long maleDomEmployee, Long femaleDomEmployee,
                               Long maleOverseasEmployee, Long femaleOverseasEmployee, Long enteringRate,
                               Long outSchoolScholarship, Long inSchoolScholarship, Long scholarshipPerPerson,
                               Long numOfFulltimeProfessor, Long thesisResultPerProfessor, Long rearchCostPerProfessor,
                               Long employmentRate) {
        this.year = year;
        this.departmentId = departmentId;
        this.admissionFee = admissionFee;
        this.tuition = tuition;
        this.maleGr = maleGr;
        this.femaleGr = femaleGr;
        this.enteringDomCmntyColl = enteringDomCmntyColl;
        this.enteringOverseasCmntyColl = enteringOverseasCmntyColl;
        this.enteringDomUniv = enteringDomUniv;
        this.enteringOverseasUniv = enteringOverseasUniv;
        this.enteringDomGrSchool = enteringDomGrSchool;
        this.enteringOverseasGrSchool = enteringOverseasGrSchool;
        this.domScholarNumber = domScholarNumber;
        this.overseasScholarNumber = overseasScholarNumber;
        this.maleEmploymentTarget = maleEmploymentTarget;
        this.femaleEmploymentTarget = femaleEmploymentTarget;
        this.maleDomEmployee = maleDomEmployee;
        this.femaleDomEmployee = femaleDomEmployee;
        this.maleOverseasEmployee = maleOverseasEmployee;
        this.femaleOverseasEmployee = femaleOverseasEmployee;
        this.enteringRate = enteringRate;
        this.outSchoolScholarship = outSchoolScholarship;
        this.inSchoolScholarship = inSchoolScholarship;
        this.scholarshipPerPerson = scholarshipPerPerson;
        this.numOfFulltimeProfessor = numOfFulltimeProfessor;
        this.thesisResultPerProfessor = thesisResultPerProfessor;
        this.rearchCostPerProfessor = rearchCostPerProfessor;
        this.employmentRate = employmentRate;
    }

    public Long getYear() {
        return this.year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Long getAdmissionFee() {
        return this.admissionFee;
    }

    public void setAdmissionFee(Long admissionFee) {
        this.admissionFee = admissionFee;
    }

    public Long getTuition() {
        return this.tuition;
    }

    public void setTuition(Long tuition) {
        this.tuition = tuition;
    }

    public Long getMaleGr() {
        return this.maleGr;
    }

    public void setMaleGr(Long maleGr) {
        this.maleGr = maleGr;
    }

    public Long getFemaleGr() {
        return this.femaleGr;
    }

    public void setFemaleGr(Long femaleGr) {
        this.femaleGr = femaleGr;
    }

    public Long getEnteringDomCmntyColl() {
        return this.enteringDomCmntyColl;
    }

    public void setEnteringDomCmntyColl(Long enteringDomCmntyColl) {
        this.enteringDomCmntyColl = enteringDomCmntyColl;
    }

    public Long getEnteringOverseasCmntyColl() {
        return this.enteringOverseasCmntyColl;
    }

    public void setEnteringOverseasCmntyColl(Long enteringOverseasCmntyColl) {
        this.enteringOverseasCmntyColl = enteringOverseasCmntyColl;
    }

    public Long getEnteringDomUniv() {
        return this.enteringDomUniv;
    }

    public void setEnteringDomUniv(Long enteringDomUniv) {
        this.enteringDomUniv = enteringDomUniv;
    }

    public Long getEnteringOverseasUniv() {
        return this.enteringOverseasUniv;
    }

    public void setEnteringOverseasUniv(Long enteringOverseasUniv) {
        this.enteringOverseasUniv = enteringOverseasUniv;
    }

    public Long getEnteringDomGrSchool() {
        return this.enteringDomGrSchool;
    }

    public void setEnteringDomGrSchool(Long enteringDomGrSchool) {
        this.enteringDomGrSchool = enteringDomGrSchool;
    }

    public Long getEnteringOverseasGrSchool() {
        return this.enteringOverseasGrSchool;
    }

    public void setEnteringOverseasGrSchool(Long enteringOverseasGrSchool) {
        this.enteringOverseasGrSchool = enteringOverseasGrSchool;
    }

    public Long getDomScholarNumber() {
        return this.domScholarNumber;
    }

    public void setDomScholarNumber(Long domScholarNumber) {
        this.domScholarNumber = domScholarNumber;
    }

    public Long getOverseasScholarNumber() {
        return this.overseasScholarNumber;
    }

    public void setOverseasScholarNumber(Long overseasScholarNumber) {
        this.overseasScholarNumber = overseasScholarNumber;
    }

    public Long getMaleEmploymentTarget() {
        return this.maleEmploymentTarget;
    }

    public void setMaleEmploymentTarget(Long maleEmploymentTarget) {
        this.maleEmploymentTarget = maleEmploymentTarget;
    }

    public Long getFemaleEmploymentTarget() {
        return this.femaleEmploymentTarget;
    }

    public void setFemaleEmploymentTarget(Long femaleEmploymentTarget) {
        this.femaleEmploymentTarget = femaleEmploymentTarget;
    }

    public Long getMaleDomEmployee() {
        return this.maleDomEmployee;
    }

    public void setMaleDomEmployee(Long maleDomEmployee) {
        this.maleDomEmployee = maleDomEmployee;
    }

    public Long getFemaleDomEmployee() {
        return this.femaleDomEmployee;
    }

    public void setFemaleDomEmployee(Long femaleDomEmployee) {
        this.femaleDomEmployee = femaleDomEmployee;
    }

    public Long getMaleOverseasEmployee() {
        return this.maleOverseasEmployee;
    }

    public void setMaleOverseasEmployee(Long maleOverseasEmployee) {
        this.maleOverseasEmployee = maleOverseasEmployee;
    }

    public Long getFemaleOverseasEmployee() {
        return this.femaleOverseasEmployee;
    }

    public void setFemaleOverseasEmployee(Long femaleOverseasEmployee) {
        this.femaleOverseasEmployee = femaleOverseasEmployee;
    }

    public Long getEnteringRate() {
        return this.enteringRate;
    }

    public void setEnteringRate(Long enteringRate) {
        this.enteringRate = enteringRate;
    }

    public Long getOutSchoolScholarship() {
        return this.outSchoolScholarship;
    }

    public void setOutSchoolScholarship(Long outSchoolScholarship) {
        this.outSchoolScholarship = outSchoolScholarship;
    }

    public Long getInSchoolScholarship() {
        return this.inSchoolScholarship;
    }

    public void setInSchoolScholarship(Long inSchoolScholarship) {
        this.inSchoolScholarship = inSchoolScholarship;
    }

    public Long getScholarshipPerPerson() {
        return this.scholarshipPerPerson;
    }

    public void setScholarshipPerPerson(Long scholarshipPerPerson) {
        this.scholarshipPerPerson = scholarshipPerPerson;
    }

    public Long getNumOfFulltimeProfessor() {
        return this.numOfFulltimeProfessor;
    }

    public void setNumOfFulltimeProfessor(Long numOfFulltimeProfessor) {
        this.numOfFulltimeProfessor = numOfFulltimeProfessor;
    }

    public Long getThesisResultPerProfessor() {
        return this.thesisResultPerProfessor;
    }

    public void setThesisResultPerProfessor(Long thesisResultPerProfessor) {
        this.thesisResultPerProfessor = thesisResultPerProfessor;
    }

    public Long getRearchCostPerProfessor() {
        return this.rearchCostPerProfessor;
    }

    public void setRearchCostPerProfessor(Long rearchCostPerProfessor) {
        this.rearchCostPerProfessor = rearchCostPerProfessor;
    }

    public Long getEmploymentRate() {
        return this.employmentRate;
    }

    public void setEmploymentRate(Long employmentRate) {
        this.employmentRate = employmentRate;
    }
}

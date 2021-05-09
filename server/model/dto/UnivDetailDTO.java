package server.model.dto;

import java.io.Serializable;

public class UnivDetailDTO implements Serializable {
    private Long year;
    private String univId;
    private Long studentNumber;
    private Long admissionCompetitionRate;
    private Long employmentRate;
    private Long enteringRate;
    private Long educationCostPerPerson;
    private Long totalScholarshipBenefits;
    private Long numberFounders;
    private Long startCompanySales;
    private Long startCompanyCapital;
    private Long schoolStartCompanyFund;
    private Long governmentStartCompanyFund;
    private Long professorForStartCompany;
    private Long staffForStartCompany;
    private Long admissionFee;
    private Long averageTuition;
    private Long humanitiesSocialTuition;
    private Long naturalScienceTuition;
    private Long artMusPhysTuition;
    private Long engineeringTuition;
    private Long medicalTuition;

    public UnivDetailDTO(Long year, String univId, Long studentNumber, Long admissionCompetitionRate, Long employmentRate,
                         Long enteringRate, Long educationCostPerPerson, Long totalScholarshipBenefits, Long numberFounders,
                         Long startCompanySales, Long startCompanyCapital, Long schoolStartCompanyFund,
                         Long governmentStartCompanyFund, Long professorForStartCompany, Long staffForStartCompany,
                         Long admissionFee, Long averageTuition, Long humanitiesSocialTuition, Long naturalScienceTuition,
                         Long artMusPhysTuition, Long engineeringTuition, Long medicalTuition) {

        this.year = year;
        this.univId = univId;
        this.studentNumber = studentNumber;
        this.admissionCompetitionRate = admissionCompetitionRate;
        this.employmentRate = employmentRate;
        this.enteringRate = enteringRate;
        this.educationCostPerPerson = educationCostPerPerson;
        this.totalScholarshipBenefits = totalScholarshipBenefits;
        this.numberFounders = numberFounders;
        this.startCompanySales = startCompanySales;
        this.startCompanyCapital = startCompanyCapital;
        this.schoolStartCompanyFund = schoolStartCompanyFund;
        this.governmentStartCompanyFund = governmentStartCompanyFund;
        this.professorForStartCompany = professorForStartCompany;
        this.staffForStartCompany = staffForStartCompany;
        this.admissionFee = admissionFee;
        this.averageTuition = averageTuition;
        this.humanitiesSocialTuition = humanitiesSocialTuition;
        this.naturalScienceTuition = naturalScienceTuition;
        this.artMusPhysTuition = artMusPhysTuition;
        this.engineeringTuition = engineeringTuition;
        this.medicalTuition = medicalTuition;

    }

    public Long getYear() {
        return this.year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getUnivId() {
        return this.univId;
    }

    public void setUnivId(String univId) {
        this.univId = univId;
    }

    public Long getStudentNumber() {
        return this.studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Long getAdmissionCompetitionRate() {
        return this.admissionCompetitionRate;
    }

    public void setAdmissionCompetitionRate(Long admissionCompetitionRate) {
        this.admissionCompetitionRate = admissionCompetitionRate;
    }

    public Long getEmploymentRate() {
        return this.employmentRate;
    }

    public void setEmploymentRate(Long employmentRate) {
        this.employmentRate = employmentRate;
    }

    public Long getEnteringRate() {
        return this.enteringRate;
    }

    public void setEnteringRate(Long enteringRate) {
        this.enteringRate = enteringRate;
    }

    public Long getEducationCostPerPerson() {
        return this.educationCostPerPerson;
    }

    public void setEducationCostPerPerson(Long educationCostPerPerson) {
        this.educationCostPerPerson = educationCostPerPerson;
    }

    public Long getTotalScholarshipBenefits() {
        return this.totalScholarshipBenefits;
    }

    public void setTotalScholarshipBenefits(Long totalScholarshipBenefits) {
        this.totalScholarshipBenefits = totalScholarshipBenefits;
    }

    public Long getNumberFounders() {
        return this.numberFounders;
    }

    public void setNumberFounders(Long numberFounders) {
        this.numberFounders = numberFounders;
    }

    public Long getStartCompanySales() {
        return this.startCompanySales;
    }

    public void setStartCompanySales(Long startCompanySales) {
        this.startCompanySales = startCompanySales;
    }

    public Long getStartCompanyCapital() {
        return this.startCompanyCapital;
    }

    public void setStartCompanyCapital(Long startCompanyCapital) {
        this.startCompanyCapital = startCompanyCapital;
    }

    public Long getSchoolStartCompanyFund() {
        return this.schoolStartCompanyFund;
    }

    public void setSchoolStartCompanyFund(Long schoolStartCompanyFund) {
        this.schoolStartCompanyFund = schoolStartCompanyFund;
    }

    public Long getGovernmentStartCompanyFund() {
        return this.governmentStartCompanyFund;
    }

    public void setGovernmentStartCompanyFund(Long governmentStartCompanyFund) {
        this.governmentStartCompanyFund = governmentStartCompanyFund;
    }

    public Long getProfessorForStartCompany() {
        return this.professorForStartCompany;
    }

    public void setProfessorForStartCompany(Long professorForStartCompany) {
        this.professorForStartCompany = professorForStartCompany;
    }

    public Long getStaffForStartCompany() {
        return this.staffForStartCompany;
    }

    public void setStaffForStartCompany(Long staffForStartCompany) {
        this.staffForStartCompany = staffForStartCompany;
    }

    public Long getAdmissionFee() {
        return this.admissionFee;
    }

    public void setAdmissionFee(Long admissionFee) {
        this.admissionFee = admissionFee;
    }

    public Long getAverageTuition() {
        return this.averageTuition;
    }

    public void setAverageTuition(Long averageTuition) {
        this.averageTuition = averageTuition;
    }

    public Long getHumanitiesSocialTuition() {
        return this.humanitiesSocialTuition;
    }

    public void setHumanitiesSocialTuition(Long humanitiesSocialTuition) {
        this.humanitiesSocialTuition = humanitiesSocialTuition;
    }

    public Long getNaturalScienceTuition() {
        return this.naturalScienceTuition;
    }

    public void setNaturalScienceTuition(Long naturalScienceTuition) {
        this.naturalScienceTuition = naturalScienceTuition;
    }

    public Long getArtMusPhysTuition() {
        return this.artMusPhysTuition;
    }

    public void setArtMusPhysTuition(Long artMusPhysTuition) {
        this.artMusPhysTuition = artMusPhysTuition;
    }

    public Long getEngineeringTuition() {
        return this.engineeringTuition;
    }

    public void setEngineeringTuition(Long engineeringTuition) {
        this.engineeringTuition = engineeringTuition;
    }

    public Long getMedicalTuition() {
        return this.medicalTuition;
    }

    public void setMedicalTuition(Long medicalTuition) {
        this.medicalTuition = medicalTuition;
    }
}

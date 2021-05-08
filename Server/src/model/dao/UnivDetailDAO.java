package model.dao;

import model.DBCP;
import model.dto.UnivDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnivDetailDAO {

    public UnivDetailDTO select(String univID) throws Exception {

        Connection conn = DBCP.getConnection();

        String preQuery = "SELECT * FROM crtvp.\"univ_detail\" WHERE univ_id = ?";

        ResultSet rs = null;
        UnivDetailDTO dto = null;

        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setString(1, univID);

            rs = pstmt.executeQuery();


            Long year = rs.getLong("year");
            String univId = rs.getString("univ_id");
            Long studentNumber = rs.getLong("student_number");
            Long admissionCompetitionRate = rs.getLong("admission_competition_rate");
            Long employmentRate = rs.getLong("employment_rate");
            Long enteringRate = rs.getLong("entering_rate");
            Long educationCostPerPerson = rs.getLong("education_cost_per_person");
            Long totalScholarshipBenefits = rs.getLong("total_scholarship_benefits");
            Long numberFounders = rs.getLong("number_founders");
            Long startCompanySales = rs.getLong("start_company_sales");
            Long startCompanyCapital = rs.getLong("start_company_capital");
            Long schoolStartCompanyFund = rs.getLong("school_start_company_fund");
            Long governmentStartCompanyFund = rs.getLong("government_start_company_fund");
            Long professorForStartCompany = rs.getLong("professor_for_start_company");
            Long staffForStartCompany = rs.getLong("staff_for_start_company");
            Long admissionFee = rs.getLong("admission_fee");
            Long averageTuition = rs.getLong("average_tuition");
            Long humanitiesSocialTuition = rs.getLong("humanities_social_tuition");
            Long naturalScienceTuition = rs.getLong("natural_science_tuition");
            Long artMusPhysTuition = rs.getLong("art_mus_phys_tuition");
            Long engineeringTuition = rs.getLong("engineering_tuition");
            Long medicalTuition = rs.getLong("medical_tuition");



            dto = new UnivDetailDTO(year, univId, studentNumber, admissionCompetitionRate, employmentRate, enteringRate,
                    educationCostPerPerson, totalScholarshipBenefits, numberFounders, startCompanySales,
                    startCompanyCapital, schoolStartCompanyFund, governmentStartCompanyFund, professorForStartCompany,
                    staffForStartCompany, admissionFee, averageTuition, humanitiesSocialTuition, naturalScienceTuition,
                    artMusPhysTuition, engineeringTuition, medicalTuition);


        } catch (SQLException sqle) {
            System.out.println("Exception : SELECT");
            sqle.printStackTrace();

        } finally {
            if (conn != null)
                DBCP.returnConnection(conn);
            if (rs != null)
                try { rs.close(); } catch(SQLException sqle){System.out.println("Exception : SELECT"); sqle.printStackTrace();}

        }

        return dto;
    }

}

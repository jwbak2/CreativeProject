package Server.model.dao;

import Server.model.DBCP;
import Server.model.MinMax;
import Server.model.MinMaxOfIndicator;
import Server.model.dto.UnivDetailDTO;
import Server.model.Cache;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UnivDetailDAO {

    // id로 select 연산 수행
    public UnivDetailDTO select(String univID, int univYear) throws Exception {

        Connection conn = DBCP.getConnection();

        String preQuery = "SELECT * FROM crtvp.univ_detail WHERE univ_id = ? AND year = ?";

        ResultSet rs = null;
        UnivDetailDTO dto = null;

        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setString(1, univID);
            pstmt.setInt(2, univYear);

            rs = pstmt.executeQuery();
            rs.next();

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
            Long dormitoryAccommodationRate = rs.getLong("DORMITORY_ACCOMMODATION_RATE");
            Long dispatchedStudent = rs.getLong("DISPATCHED_STUDENT");
            Long bookTotal = rs.getLong("BOOK_TOTAL");
            Long univArea = rs.getLong("UNIV_AREA");
            Long numOfFulltimeProfessor = rs.getLong("NUM_OF_FULLTIME_PROFESSOR");
            Long researchCostPerProfessor = rs.getLong("RESEARCH_COST_PER_PROFESSOR");
            Long numOfPatentRegistration = rs.getLong("NUM_OF_PATENT_REGISTRATION");


            dto = new UnivDetailDTO(year, univId, studentNumber, employmentRate, admissionCompetitionRate,
                        enteringRate, educationCostPerPerson, totalScholarshipBenefits, numberFounders,
                        startCompanySales, startCompanyCapital, schoolStartCompanyFund, governmentStartCompanyFund,
                        professorForStartCompany, staffForStartCompany, admissionFee, averageTuition, humanitiesSocialTuition,
                        naturalScienceTuition, artMusPhysTuition, engineeringTuition, medicalTuition, dormitoryAccommodationRate,
                        dispatchedStudent, bookTotal, univArea, numOfFulltimeProfessor, researchCostPerProfessor, numOfPatentRegistration);


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

    // 속성의 MIN, MAX 데이터 가져오기
    public MinMaxOfIndicator[] selectMinMax(int year, String[] indicator) throws Exception {

        Connection conn = DBCP.getConnection();
        StringBuilder sb = new StringBuilder();

        // Query 만들기
        sb.append("SELECT ");
        for (int i = 0; i < indicator.length; i++) {
            sb.append("MAX(" + indicator[i] + "),");
            sb.append("MIN(" + indicator[i] + ")");

            if (i < indicator.length - 1)
                sb.append(",");
        }
        sb.append(" FROM crtvp.univ_detail WHERE year = ?");

        String preQuery = sb.toString();

        ResultSet rs = null;
        MinMaxOfIndicator[] minMaxData = null;

        // 실행
        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setLong(1, year);

            rs = pstmt.executeQuery();
            rs.next();


            minMaxData = new MinMaxOfIndicator[indicator.length];

            // MIN, MAX 저장
            for (int i = 0; i < indicator.length; i++) {
                Long max = rs.getLong("MAX(" + indicator[i] + ")");
                Long min = rs.getLong("MIN(" + indicator[i] + ")");
                minMaxData[i] = new MinMaxOfIndicator(year, indicator[i], min, max);
            }


        } catch (SQLException sqle) {
            System.out.println("Exception : SELECT");
            sqle.printStackTrace();

        } finally {
            if (conn != null)
                DBCP.returnConnection(conn);
            if (rs != null)
                try { rs.close(); } catch(SQLException sqle){System.out.println("Exception : SELECT"); sqle.printStackTrace();}

        }

        return minMaxData;

    }

    public double calculateScoreByYear(int year, String univId, ArrayList<String> idct) {

        Connection conn = DBCP.getConnection();

        String preQuery = "SELECT * FROM crtvp.univ_detail WHERE univ_id = ? AND year = ?";

        ResultSet rs = null;
        double score = 0.0;

        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setString(1, univId);
            pstmt.setInt(2, year);

            rs = pstmt.executeQuery();
            rs.next();

            HashMap<String, MinMax> minMaxList = Cache.getMinMaxOfIndicators();

            int size =  idct.size();
            for (int i = 0; i < size; i++) {
                String indicator = idct.get(i);
                System.out.println("#지표 : " + indicator);


                MinMax minMax = minMaxList.get(indicator);
                long min = minMax.getMin();
                long max = minMax.getMax();

                System.out.println("#min : " + min);
                System.out.println("#max : " + max);

                long value = rs.getLong(indicator);


                // 0 <= score <= 1
                score += ((double) value - min) / (max - min);

            }

            score = score / size;


        } catch (SQLException sqle) {
            System.out.println("Exception : SELECT");
            sqle.printStackTrace();

        } finally {
            if (conn != null)
                DBCP.returnConnection(conn);
            if (rs != null)
                try { rs.close(); } catch(SQLException sqle){System.out.println("Exception : SELECT"); sqle.printStackTrace();}

        }

        return score;
    }

}
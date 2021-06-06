package Server.model.dao;

import Server.model.Cache;
import Server.model.DBCP;
import Server.model.MinMax;
import Server.model.MinMaxOfIndicator;
import Server.model.dto.DepartmentDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentDetailDAO {

	//id로 select 연산 수행
	public DepartmentDetailDTO select(String deptID, int deptYear) {

		Connection conn = DBCP.getConnection();

		String preQuery = "SELECT * FROM crtvp.department_detail WHERE department_id = ? and year = ?";

		ResultSet rs = null;
		DepartmentDetailDTO dto = null;

		try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

			pstmt.setString(1, deptID);
			pstmt.setInt(2, deptYear);

			rs = pstmt.executeQuery();
			rs.next();

			Long year = rs.getLong("year");
			String deptId = rs.getString("department_id");
			Long admissionFee = rs.getLong("admission_fee");
			Long tuition = rs.getLong("tuition");
			Long maleGr = rs.getLong("male_gr");
			Long femaleGr = rs.getLong("female_gr");
			Long enteringDomCmntyColl = rs.getLong("entering_dom_cmnty_coll");
			Long enteringOverseasCmntyColl = rs.getLong("entering_overseas_cmnty_coll");
			Long enteringDomUniv = rs.getLong("entering_dom_univ");
			Long enteringOverseasUniv = rs.getLong("entering_overseas_univ");
			Long enteringDomGrSchool = rs.getLong("entering_dom_gr_school");
			Long enteringOverseasGrSchool = rs.getLong("entering_overseas_gr_school");
			Long domScholarNumber = rs.getLong("dom_scholar_number");
			Long overseasScholarNumber = rs.getLong("overseas_scholar_number");
			Long maleEmploymentTarget = rs.getLong("male_employment_target");
			Long femaleEmploymentTarget = rs.getLong("female_employment_target");
			Long maleDomEmployee = rs.getLong("male_dom_employee");
			Long femaleDomEmployee = rs.getLong("female_dom_employee");
			Long maleOverseasEmployee = rs.getLong("male_overseas_employee");
			Long femaleOverseasEmployee = rs.getLong("female_overseas_employee");
			Long enteringRate = rs.getLong("entering_rate");
			Long employmentRate = rs.getLong("employment_rate");
			Long outSchoolScholarship = rs.getLong("out_school_scholarship");
			Long inSchoolScholarship = rs.getLong("in_school_scholarship");
			Long scholarshipPerPerson = rs.getLong("scholarship_per_person");
			Long numOfFulltimeProfessor = rs.getLong("num_of_fulltime_professor");
			Long thesisResultPerProfessor = rs.getLong("thesis_result_per_professor");
			Long rearchCostPerProfessor = rs.getLong("rearch_cost_per_professor");


			dto = new DepartmentDetailDTO(year, deptId, admissionFee, tuition, maleGr, femaleGr,
					enteringDomCmntyColl, enteringOverseasCmntyColl, enteringDomUniv, enteringOverseasUniv,
					enteringDomGrSchool, enteringOverseasGrSchool, domScholarNumber, overseasScholarNumber,
					maleEmploymentTarget, femaleEmploymentTarget, maleDomEmployee, femaleDomEmployee, maleOverseasEmployee,
					femaleOverseasEmployee, enteringRate, outSchoolScholarship, inSchoolScholarship, scholarshipPerPerson,
					numOfFulltimeProfessor, thesisResultPerProfessor, rearchCostPerProfessor, employmentRate);


		} catch (SQLException sqle) {
			System.out.println("Exception : SELECT");
			sqle.printStackTrace();

		} finally {
			if (conn != null) {
				DBCP.returnConnection(conn);
			} if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					System.out.println("Exception : SELECT");
					sqle.printStackTrace();
				}
			}

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
		sb.append(" FROM crtvp.department_detail WHERE year = ?");

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

	public double calculateScoreByYear(int year, String deptId, ArrayList<String> idct) {

		Connection conn = DBCP.getConnection();

		String preQuery = "SELECT * FROM crtvp.department_detail WHERE department_id = ? AND year = ?";

		ResultSet rs = null;
		double score = 0.0;

		try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

			pstmt.setString(1, deptId);
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

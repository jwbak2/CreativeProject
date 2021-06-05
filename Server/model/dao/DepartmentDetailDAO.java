package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.DepartmentDetailDTO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}

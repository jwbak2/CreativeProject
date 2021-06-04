package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.DepartmentDetailDTO;
import Server.model.dto.UnivDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentDAO {

	public HashMap<String, String> getDepartmentList(String univCode) {
		// 학과 리스트 반환

		HashMap<String, String> departmentList = null;     // 학과 목록 선언 '학과 이름' 의 1차원 배열

		String SQL = "SELECT \"department_name\", \"department_id\" FROM crtvp.\"department\" WHERE \"univ_id\" = ?";

		Connection conn = DBCP.getConnection();
		ResultSet rs = null;

		try(PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

			pstmt.setString(1, univCode);

			rs = pstmt.executeQuery();

			// 학과 리스트를 저장할 HashMap
			departmentList = new HashMap<>();

			while (rs.next()) {
				departmentList.put(rs.getString("department_name"), rs.getString("department_id"));
			}

		} catch (SQLException sqle) {
			System.out.println("Exception : SELECT");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

		return departmentList;
	}

	public String getDeptID(String univID, String deptName) {

		Connection conn = DBCP.getConnection();

		String preQuery = "SELECT \"department_id\" FROM crtvp.\"department\" WHERE \"univ_id\" = ? AND \"department_name\" = ?";

		ResultSet rs = null;
		String deptId = null;

		try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

			pstmt.setString(1, univID);
			pstmt.setString(2, deptName);

			rs = pstmt.executeQuery();
			rs.next();

			deptId = rs.getString("department_id");

		} catch (SQLException sqle) {
			System.out.println("Exception : SELECT");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);
			if (rs != null)
				try { rs.close(); } catch(SQLException sqle){System.out.println("Exception : SELECT"); sqle.printStackTrace();}

		}

		return deptId;
	}

}

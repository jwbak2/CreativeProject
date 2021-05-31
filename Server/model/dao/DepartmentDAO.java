package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.DepartmentDetailDTO;
import Server.model.dto.UnivDTO;

import java.sql.*;

public class DepartmentDAO {

	public String[] getDepartmentList(String univCode) throws Exception {
		// 학과 리스트 반환

		String[] departmentList = null;     // 학과 목록 선언 '학과 이름' 의 1차원 배열

		String SQL = "SELECT * FROM crtvp.\"department\" WHERE \"univ_id\" = ?";

		Connection conn = DBCP.getConnection();
		ResultSet rs = null;

		try(PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

			pstmt.setString(1, univCode);

			rs = pstmt.executeQuery();

			rs.last();                      // 행 개수 세기 위해 결과셋의 마지막 행으로 이동
			int rowCount = rs.getRow();
			rs.beforeFirst();               // 처음 행으로 이동

			departmentList = new String[rowCount];

			int i = 0;
			while (rs.next()) {
				departmentList[i++] = rs.getString("department_name");
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

}

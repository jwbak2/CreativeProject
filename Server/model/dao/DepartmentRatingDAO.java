package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentRatingDAO {

	// 대학 평가 삽입
	public boolean insert(DepartmentRatingDTO rating) {

		boolean isSucc = false;

		Connection conn = DBCP.getConnection();

		String SQL = "INSERT INTO crtvp.\"user_email\", \"department_id\", \"content\", \"score\", \"creation_date\" VALUES(?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

			pstmt.setString(1, rating.getUserEmail());
			pstmt.setString(2, rating.getDepartmentId());
			pstmt.setString(3, rating.getContent());
			pstmt.setLong(4, rating.getScore());
			pstmt.setDate(5,  rating.getCreationDate());


			if(pstmt.executeUpdate() == 1) {
				isSucc = true;
			}

		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

		return isSucc;
	}

}

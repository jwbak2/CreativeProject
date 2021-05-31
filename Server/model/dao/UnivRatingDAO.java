package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.UnivRatingDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnivRatingDAO {

	// 대학 평가 삽입
//	public boolean insert(UnivRatingDTO rating) {
//		boolean isSucc = false;
//
//		Connection conn = DBCP.getConnection();
//
//		String SQL = "INSERT INTO crtvp.\"univ_rating\"(\"user_email\", \"univ_id\", \"content\", \"score\", \"creation_date\") VALUES(?,?,?,?,?)";
//
//		try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
//
//			pstmt.setString(1,rating.getUserEmail());
//			pstmt.setString(2, rating.getUnivId());
//			pstmt.setString(3, rating.getContent());
//			pstmt.setLong(4, rating.getScore());
//			pstmt.setDate(5,  rating.getCreationDate());
//
//			pstmt.executeUpdate();
//
//			isSucc = true;
//
//		} catch (SQLException sqle) {
//			System.out.println("INSERT문에서 예외 발생");
//			sqle.printStackTrace();
//			isSucc = false;
//
//		} finally {
//			if (conn != null)
//				DBCP.returnConnection(conn);
//
//		}
//
//		return isSucc;
//	}

}

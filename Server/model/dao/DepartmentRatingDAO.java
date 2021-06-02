package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.sql.*;
import java.util.ArrayList;

public class DepartmentRatingDAO {

	// 학과 평가 삽입
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

	// 학과 평가 리스트 반환
	public ArrayList<DepartmentRatingDTO> select(String deptId) {

		ArrayList<DepartmentRatingDTO> list = null;     // 학과 목록 선언 '학과 이름' 의 1차원 배열

		String SQL = "SELECT * FROM crtvp.\"department_rating\" WHERE \"department_id\" = ?";

		Connection conn = DBCP.getConnection();
		ResultSet rs = null;

		try(PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

			pstmt.setString(1, deptId);

			rs = pstmt.executeQuery();

			list = new ArrayList<>();

			while (rs.next()) {

				String userEmail = rs.getString("user_email");
				String content = rs.getString("content");
				Long score = rs.getLong("score");
				java.sql.Date creationDate = rs.getDate("creation_date");

				list.add(new DepartmentRatingDTO(userEmail, deptId, content, score, creationDate));
			}

		} catch (SQLException sqle) {
			System.out.println("Exception : SELECT");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

		return list;

	}

	// 사용자 이메일로 학과 평가 반환
	public DepartmentRatingDTO selectByEmail(String email) {

		Connection conn = DBCP.getConnection();

		String preQuery = "SELECT * FROM crtvp.\"department_rating\" WHERE \"user_email\" = ?";

		ResultSet rs = null;
		DepartmentRatingDTO dto = null;

		try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if(rs.next()) {

				String deptId = rs.getString("department_id");
				String content = rs.getString("content");
				Long score = rs.getLong("score");
				java.sql.Date creationDate = rs.getDate("creation_date");

				dto = new DepartmentRatingDTO(email, deptId, content, score, creationDate);
			}


		} catch (SQLException sqle) {
			System.out.println("Exception : SELECT__BY_EMAIL");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);
			if (rs != null)
				try { rs.close(); } catch(SQLException sqle){System.out.println("Exception : SELECT__BY_EMAIL"); sqle.printStackTrace();}

		}

		return dto;
	}

}

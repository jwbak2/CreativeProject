package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.UnivRatingDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnivRatingDAO {

	// 대학 평가 삽입
	public boolean insert(UnivRatingDTO rating) {

		boolean isSucc = false;

		Connection conn = DBCP.getConnection();

		String SQL = "INSERT INTO crtvp.univ_rating (user_email, univ_id, content, score, creation_date) VALUES(?,?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

			pstmt.setString(1, rating.getUserEmail());
			pstmt.setString(2, rating.getUnivId());
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

	// 대학 평가 리스트 반환
	public ArrayList<UnivRatingDTO> select(String univId) {

		ArrayList<UnivRatingDTO> list = null;     // 학과 목록 선언 '학과 이름' 의 1차원 배열

		String SQL = "SELECT * FROM crtvp.univ_rating WHERE univ_id = ?";

		Connection conn = DBCP.getConnection();
		ResultSet rs = null;
		System.out.println("대학 평가 리스트 조회1");

		try(PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

			pstmt.setString(1, univId);

			rs = pstmt.executeQuery();

			list = new ArrayList<>();
			System.out.println("대학 평가 리스트 조회2");

			while (rs.next()) {
				System.out.println("대학 평가 리스트 조회3");
				list.add(new UnivRatingDTO(rs.getString("user_email"), univId, rs.getString("content"),
						rs.getLong("score"), rs.getDate("creation_date")));
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

	// 사용자 이메일로 대학 평가 반환
	public UnivRatingDTO selectByEmail(String email) {

		Connection conn = DBCP.getConnection();

		String preQuery = "SELECT * FROM crtvp.univ_rating WHERE user_email = ?";

		ResultSet rs = null;
		UnivRatingDTO dto = null;

		try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if(rs.next()) {

				String univId = rs.getString("univ_id");
				String content = rs.getString("content");
				Long score = rs.getLong("score");
				java.sql.Date creationDate = rs.getDate("creation_date");

				dto = new UnivRatingDTO(email, univId, content, score, creationDate);
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

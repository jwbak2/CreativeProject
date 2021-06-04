package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.UserBookmarkDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBookmarkDAO {

	public ArrayList<String> select(String email) {
		// 북마크 리스트 반환

		ArrayList<String> list = null;     // 학과 목록 선언 '학과 이름' 의 1차원 배열

		String SQL = "SELECT univ_id FROM crtvp.user_bookmark WHERE user_email = ?";

		Connection conn = DBCP.getConnection();
		ResultSet rs = null;

		try(PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			list = new ArrayList<>();

			while (rs.next()) {
				list.add(rs.getString("univ_id"));
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
}

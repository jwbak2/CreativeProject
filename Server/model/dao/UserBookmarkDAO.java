package Server.model.dao;

import Server.model.Cache;
import Server.model.DBCP;
import Server.model.dto.UserBookmarkDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static Server.model.Cache.getKey;

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

			HashMap<String, String> univMap = Cache.getUnivList();

			while (rs.next()) {
				list.add(getKey(univMap, rs.getString("univ_id")));
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

	public boolean isOnBookmark(String univId, String email) {
		// 북마크 ON/OFF 상태 확인

		boolean isOn = false;	// 북마크 ON/OFF 상태 변수

		String SQL = "SELECT univ_id FROM crtvp.user_bookmark WHERE user_email = ?";

		Connection conn = DBCP.getConnection();
		ResultSet rs = null;

		try(PreparedStatement pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("univ_id");

				if (id.equals(univId)) {
					isOn = true;
					break;
				}
			}

		} catch (SQLException sqle) {
			System.out.println("Exception : SELECT");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

		return isOn;

	}

	public boolean toggleBookmark(String univId, String email) {

		boolean isSuccess = false;
		boolean isExist = isOnBookmark(univId, email);
		System.out.println(isExist);
		if (isExist) {
			isSuccess = deleteBookmark(univId, email);

		} else {
			isSuccess = insertBookmark(univId, email);

		}

		return isSuccess;
	}

	public boolean deleteBookmark(String univId, String email) {
		// 북마크 삭제

		int changed = 0;

		String SQL = "DELETE FROM crtvp.user_bookmark WHERE user_email = ? AND univ_id = ?";

		Connection conn = DBCP.getConnection();

		try(PreparedStatement pstmt = conn.prepareStatement(SQL)) {

			pstmt.setString(1, email);
			pstmt.setString(2, univId);

			System.out.println("북마크 삭제 준비 완료");
			changed = pstmt.executeUpdate();
			System.out.println("북마크 삭제 완료");

		} catch (SQLException sqle) {
			System.out.println("Exception : DELETE BOOKMARK");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

		return changed > 0;
	}

	public boolean insertBookmark(String univId, String email) {
		// 북마크 추가

		int changed = 0;

		String SQL = "INSERT INTO crtvp.user_bookmark(user_email, univ_id) VALUES (?, ?)";

		Connection conn = DBCP.getConnection();

		try(PreparedStatement pstmt = conn.prepareStatement(SQL)) {

			pstmt.setString(1, email);
			pstmt.setString(2, univId);

			System.out.println("북마크 등록 준비 완료");
			changed = pstmt.executeUpdate();
			System.out.println("북마크 등록 완료");

		} catch (SQLException sqle) {
			System.out.println("Exception : INSERT BOOKMARK");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

		return changed > 0;
	}


}

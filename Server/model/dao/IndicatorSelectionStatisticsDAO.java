package Server.model.dao;

import Client.vo.RankVO;
import Server.model.DBCP;

import java.sql.*;
import java.util.ArrayList;

public class IndicatorSelectionStatisticsDAO {

	public void increaseIndicatorView(String idctName) {

		Connection conn = DBCP.getConnection();

		String preQuery = "UPDATE crtvp.Indicator_selection_statistics SET selection_number = selection_number + 1 WHERE indicator_name = ?";


		try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

			pstmt.setString(1, idctName);

			pstmt.executeQuery();

		} catch (SQLException sqle) {
			System.out.println("Exception : SELECT");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

	}

	public ArrayList<RankVO> getIndicatorOfView() {
		final int LIST_SIZE = 20;

		// 학과
		ArrayList<RankVO> list = null;

		String SQL = "SELECT indicator_name, selection_number FROM crtvp.Indicator_selection_statistics ORDER BY selection_number DESC";

		Connection conn = DBCP.getConnection();

		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)
		) {

			// 대학 리스트를 저장할 HashMap
			list = new ArrayList<>();

			int count = 0;
			while (rs.next()) {
				count++;
				list.add(new RankVO(rs.getString("indicator_name"), rs.getInt("selection_number")));

				if (count == LIST_SIZE)
					break;

			}

		} catch (
				SQLException sqle) {
			System.out.println("Exception : SELECT");
			sqle.printStackTrace();

		} finally {
			if (conn != null)
				DBCP.returnConnection(conn);

		}

		return list;
	}
}

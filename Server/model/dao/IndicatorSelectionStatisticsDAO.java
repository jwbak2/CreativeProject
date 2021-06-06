package Server.model.dao;

import Client.vo.RankVO;
import Server.model.DBCP;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IndicatorSelectionStatisticsDAO {

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

package Server.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import Client.vo.RankVO;
import Server.model.DBCP;
import Server.model.dto.UnivDTO;

public class UnivDAO {

    //id로 select 연산 수행
    public UnivDTO select(String univID) {

        Connection conn = DBCP.getConnection();

        String preQuery = "SELECT * FROM crtvp.univ WHERE univ_id = ?";

        ResultSet rs = null;
        UnivDTO dto = null;

        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setString(1, univID);

            rs = pstmt.executeQuery();
            rs.next();

            String univId = rs.getString("univ_id");
            String univName = rs.getString("univ_name");
            String univType = rs.getString("univ_type");
            String univEstablishmentCls = rs.getString("univ_establishment_cls");
            String univArea = rs.getString("univ_area");
            String univAddress = rs.getString("univ_address");
            String univRepresentativeNumber = rs.getString("univ_representative_number");
            String univHomepageUrl = rs.getString("univ_homepage_url");
            byte[] univLogoImageFile = rs.getBytes("univ_logo_image_file");
            String univIntroduction = rs.getString("univ_introduction");
            Long view = rs.getLong("USER_view");

            dto = new UnivDTO(univId, univName, univType, univEstablishmentCls, univArea, univAddress
                    , univRepresentativeNumber, univHomepageUrl, univLogoImageFile, univIntroduction, view);


        } catch (SQLException sqle) {
            System.out.println("Exception : SELECT");
            sqle.printStackTrace();

        } finally {
            if (conn != null)
                DBCP.returnConnection(conn);
            if (rs != null)
                try { rs.close(); } catch(SQLException sqle){System.out.println("Exception : SELECT"); sqle.printStackTrace();}

        }

        return dto;
    }

    public HashMap<String, String> getUnivList() {  // 학교 목록 반환하는 메소드

        // Key = 학교 이름,  Value = 학교 ID
        HashMap<String, String> univList = null;     // 학교 목록 선언 '학교id, 학교이름'의 HashMap

        String SQL = "SELECT * FROM CRTVP.UNIV";


        Connection conn = DBCP.getConnection();

        try(Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(SQL);
        ) {

            // 대학 리스트를 저장할 HashMap
            univList = new HashMap<>();

            while (rs.next()) {
                univList.put(rs.getString("univ_name"), rs.getString("univ_id"));
            }

        } catch (SQLException sqle) {
            System.out.println("Exception : SELECT");
            sqle.printStackTrace();

        } finally {
            if (conn != null)
                DBCP.returnConnection(conn);

        }

        return univList;
    }

    public ArrayList<RankVO> getViewList() {    // 조회수 순의 학교 리스트를 반환

        final int LIST_SIZE = 20;

        // 학과
        ArrayList<RankVO> list = null;

        String SQL = "SELECT univ_name, USER_VIEW FROM crtvp.univ ORDER BY USER_VIEW DESC";

        Connection conn = DBCP.getConnection();

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)
        ) {

            // 대학 리스트를 저장할 HashMap
            list = new ArrayList<>();

            int count = 0;
            while (rs.next()) {
                count++;
                list.add(new RankVO(rs.getString("univ_name"), rs.getInt("USER_VIEW")));

                if (count == LIST_SIZE)
                    break;
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

    public void increaseUserView(String univID) {

        Connection conn = DBCP.getConnection();

        String preQuery = "UPDATE crtvp.univ SET USER_VIEW = USER_VIEW + 1 WHERE univ_id = ?";


        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setString(1, univID);

            pstmt.executeQuery();

        } catch (SQLException sqle) {
            System.out.println("Exception : SELECT");
            sqle.printStackTrace();

        } finally {
            if (conn != null)
                DBCP.returnConnection(conn);

        }

    }

}
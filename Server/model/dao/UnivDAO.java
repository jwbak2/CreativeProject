package Server.model.dao;

import java.sql.*;
import java.util.HashMap;

import Server.model.DBCP;
import Server.model.dto.UnivDTO;

public class UnivDAO {

    //id로 select 연산 수행
    public UnivDTO select(String univID) {

        Connection conn = DBCP.getConnection();

        String preQuery = "SELECT * FROM crtvp.\"univ\" WHERE \"univ_id\" = ?";

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
//
//    public String[][] getUnivList() {  // 학교 목록 반환하는 메소드
//
//        String[][] univList = null;     // 학교 목록 선언 '학교id, 학교이름'의 2차원 배열
//
//        String SQL = "SELECT * FROM crtvp.\"univ\"";
//
//        Connection conn = DBCP.getConnection();
//
//        try(Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = stmt.executeQuery(SQL);
//        ) {
//
//            rs.last();                      // 행 개수 세기 위해 결과셋의 마지막 행으로 이동
//            int rowCount = rs.getRow();
//            rs.beforeFirst();               // 처음 행으로 이동
//
//            univList = new String[rowCount][2];
//
//            int i = 0;
//            while (rs.next()) {
//                univList[i][0] = rs.getString("univ_id");
//                univList[i][1] = rs.getString("univ_name");
//                i++;
//            }
//
//        } catch (SQLException sqle) {
//            System.out.println("Exception : SELECT");
//            sqle.printStackTrace();
//
//        } finally {
//            if (conn != null)
//                DBCP.returnConnection(conn);
//
//        }
//
//        return univList;
//    }

    public HashMap<String, String> getUnivList() {  // 학교 목록 반환하는 메소드

        // Key = 학교 이름,  Value = 학교 ID
        HashMap<String, String> univList = null;     // 학교 목록 선언 '학교id, 학교이름'의 HashMap

        String SQL = "SELECT * FROM crtvp.\"univ\"";

        Connection conn = DBCP.getConnection();

        try(Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(SQL);
        ) {

            rs.last();                      // 행 개수 세기 위해 결과셋의 마지막 행으로 이동
            int rowCount = rs.getRow();
            rs.beforeFirst();               // 처음 행으로 이동

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
}
package model.dao;

import java.sql.*;
import model.DBCP;
import model.dto.UnivDTO;

public class UnivDAO {

    //id로 select 연산 수행
    public UnivDTO select(String univID) throws Exception {

        Connection conn = DBCP.getConnection();

        String preQuery = "SELECT * FROM crtvp.\"univ\" WHERE univ_id = ?";

        ResultSet rs = null;
        UnivDTO dto = null;

        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setString(1, univID);

            rs = pstmt.executeQuery();

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
            Long view = rs.getLong("view");

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

}

package Server.model.dao;

import Server.model.DBCP;
import Server.model.dto.UnivDetailDTO;
import Server.model.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public UserDTO login(String userID, String userPW) {

        Connection conn = DBCP.getConnection();

        String preQuery = "SELECT * FROM crtvp.user WHERE user_email = ? AND user_password = ?";

        ResultSet rs = null;
        UserDTO dto = null;

        try(PreparedStatement pstmt = conn.prepareStatement(preQuery)) {

            pstmt.setString(1, userID);
            pstmt.setString(2, userPW);

            rs = pstmt.executeQuery();

            if(rs.next()) {

                String userEmail = rs.getString("user_email");
                String userPassword = rs.getString("user_password");
                String userName = rs.getString("user_name");
                Long userPhoneNumber = rs.getLong("user_phone_number");
                String userArea = rs.getString("user_area");
                Long residentRegistrationNumber = rs.getLong("resident_registration_number");
                String userCategory = rs.getString("user_category");
                String affiliatedSchool = rs.getString("affiliated_school");
                String affiliatedDepartment = rs.getString("affiliated_department");


                dto = new UserDTO(userEmail, userPassword, userName, userPhoneNumber, userArea,
                        residentRegistrationNumber, userCategory, affiliatedSchool, affiliatedDepartment);
            }


        } catch (SQLException sqle) {
            System.out.println("Exception : LOGIN");
            sqle.printStackTrace();

        } finally {
            if (conn != null)
                DBCP.returnConnection(conn);
            if (rs != null)
                try { rs.close(); } catch(SQLException sqle){System.out.println("Exception : LOGIN"); sqle.printStackTrace();}

        }

        return dto;
    }
}

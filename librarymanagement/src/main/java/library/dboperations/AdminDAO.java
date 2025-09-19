package library.dboperations;

import library.model.Admin;
import library.utils.DBUtil;
import java.sql.*;


public class AdminDAO {
    public void addAdmin(Admin admin){
        String sql = "INSERT INTO admins(username , adminId) VALUES(?,?)";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, admin.getAdminUsername());
            pstmt.setString(2, admin.getAdminId());
            pstmt.executeUpdate();
            /**
             * @param automatically set the id to a new student
             */
            try(ResultSet getGeneratedkeys = pstmt.getGeneratedKeys()){
                if(getGeneratedkeys.next()){
                    admin.setId(getGeneratedkeys.getInt(1));
                }
            }
            System.out.println(">> Admin added successfully <<");
        } catch ( Exception error){
            error.printStackTrace();
        }
    }

    public Admin getAdminByAdminId(String adminId){
        String sql = "SELECT * FROM admins WHERE adminId = ?";

        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("adminId")
                );
            }
        } catch ( Exception error){
            error.printStackTrace();
        }
        return null;
    }
}

package library.dboperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import library.utils.DBUtil;

public class StudentFinesDAO {
    
    public boolean addFine(int studentId, double amount) {
        String sql = "INSERT INTO student_fines(studentId, amount) VALUES(?, ?)";
        
        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, studentId);
            pstmt.setDouble(2, amount);
            
            pstmt.executeUpdate();
            System.out.println(">> Fine of " + amount + " added for student ID: " + studentId + " <<");
            return true;
        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }
    
    public double getTotalFinesByStudentId(int studentId) {
        String sql = "SELECT SUM(amount) as total FROM student_fines WHERE studentId = ?";
        double totalFines = 0.0;
        
        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                totalFines = rs.getDouble("total");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        
        return totalFines;
    }
}

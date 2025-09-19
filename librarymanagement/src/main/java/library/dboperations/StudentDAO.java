package library.dboperations;

import library.model.Student;
import library.utils.DBUtil;
import java.sql.*;

public class StudentDAO {
    public void addStudent(Student student){
        String sql = "INSERT INTO students(name, enrollment, password) VALUES(?,?,?)";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEnrollment());
            pstmt.setString(3, student.getPassword());
            pstmt.executeUpdate();
            /**
             * @param automatically set the id to a new student
             */
            try(ResultSet getGeneratedkeys = pstmt.getGeneratedKeys()){
                if(getGeneratedkeys.next()){
                    student.setId(getGeneratedkeys.getInt(1));
                }
            }
            System.out.println(">> Student added successfully. <<");
        } catch ( Exception error){
            error.printStackTrace();
        }
    }

    public Student getStudentByEnrollment(String enrollment){
        String sql = "SELECT * FROM students WHERE enrollment = ?";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, enrollment);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("enrollment"),
                    rs.getString("password")
                );
            }
        } catch ( Exception error){
            error.printStackTrace();
        }
        return null;
    }
}

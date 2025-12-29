package library.dboperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import library.model.BorrowedBookModel;
import library.utils.DBUtil;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import library.model.Student;


public class BorrowedBooksDAO {
    public boolean addBorrowedBook(BorrowedBookModel borrowedBook){
        // include isReturned column since DB has NOT NULL constraint on it
        String sql = "INSERT INTO borrowed_books(studentId, bookId, borrowDate, returnDate, isReturned) VALUES(?,?,?,?,?)";

        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
        ){
            pstmt.setInt(1, borrowedBook.getStudentId());
            pstmt.setInt(2, borrowedBook.getBookId());
            pstmt.setString(3, borrowedBook.getBorrowDate());
            pstmt.setString(4, borrowedBook.getReturnDate());
            // SQLite stores boolean as integer 0/1; write explicit int 0/1 to be safe
            int isReturnedInt = borrowedBook.isReturned() ? 1 : 0;
            pstmt.setInt(5, isReturnedInt);

            pstmt.executeUpdate();

            // generated keys are available but BorrowedBookModel has no setId(int) method, so just log the generated id
            try(ResultSet getGeneratedKeys = pstmt.getGeneratedKeys()){
                if(getGeneratedKeys.next()){
                    getGeneratedKeys.getInt(1);
                }
            }

            System.out.println(">> Borrowed book record added successfully <<");
            return true;
        }catch(Exception error){
            error.printStackTrace();
            return false;
        }
    }

    public List<Student> findAllStudentsWhoDidNotReturnBooksYet() throws SQLException {
        String sql = "SELECT DISTINCT s.name, s.enrollment, bb.borrowDate, b.bookName " +
            "FROM students s " +
            "INNER JOIN borrowed_books bb ON s.id = bb.studentId " +
            "INNER JOIN books b ON bb.bookId = b.id " +
            "WHERE bb.isReturned = 0";
        List<Student> students = new ArrayList<>();
        try(
            Connection conn = DBUtil.getConnection();       
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
        ){
            while(rs.next()){
                Student student = new Student(
                    rs.getString("name"),
                    rs.getString("enrollment")
                );
                students.add(student);
                // Add book details to student
                String borrowDate = rs.getString("borrowDate");
                String bookTitle = rs.getString("bookName");
                student.setBorrowDate(borrowDate);
                student.setBookTitle(bookTitle);
                

            }
        }
        return students;
    }

    public BorrowedBookModel[] getBorrowedBooksByStudentId(int studentId){
        String sql = "SELECT * FROM borrowed_books WHERE studentId = ?";
        List<BorrowedBookModel> borrowedBooks = new ArrayList<>();
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                /*
                 * Calculate fine if any book is overdue
                 * if current date - return date > 0 then set fine
                 * 1 day = 10 units fine
                 */
                double fine = 0.0;
                boolean isReturned = rs.getBoolean("isReturned");
                String returnDateStr = rs.getString("returnDate");
                
                if (!isReturned && returnDateStr != null) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate returnDate = LocalDate.parse(returnDateStr, formatter);
                        LocalDate currentDate = LocalDate.now();
                        
                        long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                        
                        if (overdueDays > 0) {
                            fine = overdueDays * 10.0;
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing date for fine calculation: " + e.getMessage());
                    }
                }
                
                BorrowedBookModel borrowedBook = new BorrowedBookModel(
                    rs.getInt("id"),
                    rs.getInt("studentId"),
                    rs.getInt("bookId"),
                    rs.getString("borrowDate"),
                    returnDateStr,
                    isReturned,
                    fine
                );
                borrowedBooks.add(borrowedBook);
            }
        }catch( Exception error){
            error.printStackTrace();
        }
        return borrowedBooks.toArray(new BorrowedBookModel[0]);
    }

    public boolean returnBook(int borrowedBookId) {
        /*
         * Return a borrowed book and calculate fine if overdue
         * Fine: 1 day overdue = 10 units
         */
        String selectSql = "SELECT * FROM borrowed_books WHERE id = ?";
        String updateSql = "UPDATE borrowed_books SET isReturned = 1 WHERE id = ?";
        String insertFineSql = "INSERT INTO student_fines(studentId, amount) VALUES(?, ?)";
        
        try (Connection conn = DBUtil.getConnection()) {
            // First, get the borrowed book details
            int studentId = 0;
            String returnDateStr = "";
            boolean alreadyReturned = false;
            
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, borrowedBookId);
                ResultSet rs = selectStmt.executeQuery();
                
                if (rs.next()) {
                    alreadyReturned = rs.getBoolean("isReturned");
                    if (alreadyReturned) {
                        System.out.println("This book has already been returned.");
                        return false;
                    }
                    
                    studentId = rs.getInt("studentId");
                    returnDateStr = rs.getString("returnDate");
                } else {
                    System.out.println("Invalid borrowed book ID.");
                    return false;
                }
            }
            
            // Calculate fine if overdue
            double fine = 0.0;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate returnDate = LocalDate.parse(returnDateStr, formatter);
                LocalDate currentDate = LocalDate.now();
                
                long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                
                if (overdueDays > 0) {
                    fine = overdueDays * 10.0;
                    System.out.println("Book is overdue by " + overdueDays + " day(s). Fine: " + fine + " units");
                    
                    // Store fine in student_fines table using same connection
                    try (PreparedStatement fineStmt = conn.prepareStatement(insertFineSql)) {
                        fineStmt.setInt(1, studentId);
                        fineStmt.setDouble(2, fine);
                        fineStmt.executeUpdate();
                        System.out.println(">> Fine of " + fine + " added for student ID: " + studentId + " <<");
                    }
                }
            } catch (Exception e) {
                System.err.println("Error calculating fine: " + e.getMessage());
            }
            
            // Update book as returned
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, borrowedBookId);
                updateStmt.executeUpdate();
                System.out.println(">> Book returned successfully <<");
                return true;
            }
            
        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }


}

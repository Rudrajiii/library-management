package library.services;
import java.sql.SQLException;
import java.util.List;
import library.model.Student;
import library.dboperations.BorrowedBooksDAO;
import library.dboperations.StudentDAO;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();
    private final BorrowedBooksDAO borrowedBooksDAO = new BorrowedBooksDAO();

    public List<Student> getAllStudents() throws SQLException{
        return studentDAO.findAll();
    }

    public List<Student> getStudentsWithOverdueBooks() throws SQLException {
        return borrowedBooksDAO.findAllStudentsWhoDidNotReturnBooksYet();
    }
}

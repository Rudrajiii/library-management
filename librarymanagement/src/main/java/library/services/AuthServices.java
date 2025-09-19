package library.services;
import library.dboperations.*;
import library.model.*;

public class AuthServices {
    private StudentDAO studentDAO;
    private AdminDAO adminDAO;

    public AuthServices() {
        this.studentDAO = new StudentDAO();
        this.adminDAO = new AdminDAO();
    }

    public String AuthoriseStudent(String enrollment , String password){
        Student getStudentInfo = studentDAO.getStudentByEnrollment(enrollment);

        if(getStudentInfo == null){
            return "Student Not Fount";
        }

        boolean isAuthorised = getStudentInfo.getPassword().equals(password);
        if(isAuthorised){
            return "Student Logged in as -> " + getStudentInfo.getName();
        }

        return "Invalid Credentials...";
    }

    public String AuthoriseAdmin(String username , String adminId){
        Admin getAdminInfo = adminDAO.getAdminByAdminId(adminId);

        if(getAdminInfo == null){
            return "Admin Not Fount";
        }

        boolean isAuthorised = getAdminInfo.getAdminUsername().equals(username);
        if(isAuthorised){
            return "Admin Logged in as -> " + getAdminInfo.getAdminUsername();
        }

        return "Invalid Credentials...";
    }

    
}

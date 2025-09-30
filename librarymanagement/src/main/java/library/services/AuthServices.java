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

    
    
    public int AuthoriseStudent(String enrollment , String password){
        Student getStudentInfo = studentDAO.getStudentByEnrollment(enrollment);

        if(getStudentInfo == null){
            return 404;
        }

        boolean isAuthorised = getStudentInfo.getPassword().equals(password);


        if(isAuthorised){
            return 200;
        }

        return 401;
    
    }

    public int AuthoriseAdmin(String username , String adminId){
        Admin getAdminInfo = adminDAO.getAdminByAdminId(adminId);

        if(getAdminInfo == null){
            return 404;
        }

        boolean isAuthorised = getAdminInfo.getAdminUsername().equals(username);

        if(isAuthorised){
            return 200;
        }

        return 401;
    }

    public String getNameViaRole(String uid , String role){
        if(role.equals("STUDENT")){
            Student getStudentInfo = studentDAO.getStudentByEnrollment(uid);
            if(getStudentInfo != null){
                return getStudentInfo.getName();
            }
        } else if (role.equals("ADMIN")){
            Admin getAdminInfo = adminDAO.getAdminByAdminId(uid);
            if(getAdminInfo != null){
                return getAdminInfo.getAdminUsername();
            }
        }
        return null;
    }

    
}

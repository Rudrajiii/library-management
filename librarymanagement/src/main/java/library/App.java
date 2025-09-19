package library;

import library.utils.DBSetup;

import java.util.Scanner;

import library.dboperations.StudentDAO;
import library.model.Student;
import library.source.StudentData;
import library.controller.HandleAdminLogin;
import library.controller.HandleStudentLogin;

import library.dboperations.AdminDAO;
import library.model.Admin;
import library.source.AdminData;




public class App {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        
        DBSetup.__init__();

        System.out.println("\n===== Library Management CLI =====");

        while(true){
            System.out.println("1. Student Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    HandleStudentLogin.handleStudentLogin();
                    return;
                case 2:
                    HandleAdminLogin.handleAdminLogin();
                    return;
                default:
                    return;
            }
        }

        // StudentDAO studentDAO = new StudentDAO();
        // AdminDAO adminDAO = new AdminDAO();

        // Student student1 = new Student( 0,"John Doe", "ENR123456", "password123");
        // studentDAO.addStudent(student1);

        // StudentData StudentData = new StudentData();
        // Student[] students = StudentData.get_student_data();

        // for(Student student : students){
        //     studentDAO.addStudent(student);
        // }

        // AdminData adminData = new AdminData();
        // Admin[] admins = adminData.get_admin_data();

        // for(Admin admin : admins){
        //     adminDAO.addAdmin(admin);
        // }

        // Student fetchedStudent = studentDAO.getStudentByEnrollment("ENR1002");
        // Admin fetchedAdmin = adminDAO.getAdminByAdminId("1234");

        // if(fetchedStudent != null){
        //     System.out.println("Fetched Student >> " + fetchedStudent);
        // } else {
        //     System.out.println("Student not found.");
        // }
        // if(fetchedAdmin != null){
        //     System.out.println("Fetched Admin >> " + fetchedAdmin);
        // } else {
        //     System.out.println("Admin not found.");
        // }
    }
}

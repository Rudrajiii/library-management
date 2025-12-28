package library;

import library.utils.DBSetup;

import java.util.Scanner;

// import library.model.Admin;
// import library.model.Student;
// import library.model.Book;

// import library.dboperations.AdminDAO;
// import library.dboperations.StudentDAO;
// import library.dboperations.BooksDAO;

// import library.source.AdminData;
// import library.source.StudentData;
// import library.source.BooksData;

import library.controller.HandleAdminLogin;
import library.controller.HandleStudentLogin;


public class App {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        
        DBSetup.__init__();

        System.out.println("\n===== Library Management CLI =====");

        while (true) {
            System.out.println("1. Student Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    HandleStudentLogin.handleStudentLogin();
                    break; // return to menu
                case 2:
                    HandleAdminLogin.handleAdminLogin();
                    break; // return to menu
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    return; // exit the program
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        // StudentDAO studentDAO = new StudentDAO();
        // AdminDAO adminDAO = new AdminDAO();
        // BooksDAO bookDAO = new BooksDAO();
        // BooksData booksData = new BooksData();
        // Book[] books = booksData.get_books();

        // for(Book book : books){
        //     bookDAO.addBook(book);
        // }

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

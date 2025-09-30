package library.controller;
import java.util.Scanner;
import library.services.AuthServices;

public class HandleStudentLogin {
    private static final Scanner scanner = new Scanner(System.in);
    public static void handleStudentLogin(){
        System.out.println("Student Enrollment : ");
        String ask_enrollment = scanner.nextLine();
        System.out.println("Student Password : ");
        String ask_password = scanner.nextLine();

        AuthServices AUTH = new AuthServices();
        int response = AUTH.AuthoriseStudent(ask_enrollment, ask_password);

        if(response == 200){
            String studentName = AUTH.getNameViaRole(ask_enrollment, "STUDENT");
            System.out.println("Login Successful as Student");
            System.out.println("Welcome XD " + studentName);
            HandleBooksUnderStudent.handleBookEvents();

        } else if (response == 404) {
            System.out.println("Student Not Found");
        } else {
            System.out.println("Invalid Credentials");
        }

    }
}

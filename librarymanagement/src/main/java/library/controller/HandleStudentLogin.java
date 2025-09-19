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
        String response = AUTH.AuthoriseStudent(ask_enrollment, ask_password);

        System.out.println(response);
    }
}

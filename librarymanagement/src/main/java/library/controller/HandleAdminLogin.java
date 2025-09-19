package library.controller;
import library.services.AuthServices;
import java.util.Scanner;

public class HandleAdminLogin {
    private static final Scanner scanner = new Scanner(System.in);

    public static void handleAdminLogin(){
        System.out.println("Admin Username : ");
        String ask_username = scanner.nextLine();
        System.out.println("Admin Id : ");
        String ask_adminId = scanner.nextLine();

        AuthServices AUTH = new AuthServices();
        String response = AUTH.AuthoriseAdmin(ask_username , ask_adminId);

        System.out.println(response);
    }
}
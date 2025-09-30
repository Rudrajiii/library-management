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
        int response = AUTH.AuthoriseAdmin(ask_username , ask_adminId);

        if(response == 200){
            String adminName = AUTH.getNameViaRole(ask_adminId, "ADMIN");
            System.out.println("Login Successful as Admin");
            System.out.println("Welcome XD " + adminName);
        } else if (response == 404) {
            System.out.println("Admin Not Found");
        } else {
            System.out.println("Invalid Credentials");
        }
    }
}
package library.controller;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HandleEventsUnderAdmin {
    private static final Scanner scanner = new Scanner(System.in);

    private static void viewAllBooks() {
        HandleBooksUnderStudent.showAllBooks();
    }

    private static void borrowBook() {
        // Implementation for borrowing a book
        System.out.println("Borrowing a book...");
    }

    private static void viewMyBorrowedBooks() {
        // Implementation for viewing borrowed books
        System.out.println("Viewing my borrowed books...");
    }

    private static void returnBook() {
        System.out.println("Returning a book...");
    }

    public static void handleAdminEvents(){
        while(true){
            System.out.println("\n--- Admin Menu ---");
            System.out.println("[ADMIN] 1. View All Books");
            System.out.println("[ADMIN] 3. View My Borrowed Books");
            System.out.println("[ADMIN] 4. Return a Book");
            System.out.println("[ADMIN] 5. Logout");
            System.out.print("[ADMIN] Enter Your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllBooks();
                    break;
                case "2":
                    borrowBook();
                    break;
                case "3":
                    viewMyBorrowedBooks();
                    break;
                case "4":
                    returnBook();
                    break;
                case "5":
                    System.out.println("[ADMIN] Logging out...");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        return;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                default:
                    System.out.println("[ADMIN] Invalid choice. Please try again.");
                    continue;
            }
        }
    }
}

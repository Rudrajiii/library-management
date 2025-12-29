package library.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import library.model.Book;
import library.services.BookService;
import library.services.StudentService;

public class HandleEventsUnderAdmin {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookService bookService = new BookService();
    private static final StudentService studentService = new StudentService();

    private static void viewAllBooks() {
        HandleBooksUnderStudent.showAllBooks();
    }

    private static void addBooksToLibrary() {
        try{
            System.out.print("Enter book title: ");
            String bookName = scanner.nextLine();
            System.out.print("Enter book availability: ");
            int availability = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            Book book = new Book(bookName, availability);
            bookService.addBook(book);
            System.out.println(">> Book added successfully <<");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private static void viewStudents() {
        try {
            var students = studentService.getAllStudents();
            System.out.println("\n--- List of Students ---");
            for (var student : students) {
                System.out.println("Name: " + student.getName() + ", Enrollment: " + student.getEnrollment());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    private static void viewStudentsWhoDidntReturnBooks() {
        System.out.println("Viewing students who didn't return books...");
        try{
            var students = studentService.getStudentsWithOverdueBooks();
            System.out.println("\n--- Students Who Didn't Return Books ---");
            for (var student : students) {
                /*
                total borrowed days calculations
                */
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate borrowedDate = LocalDate.parse(student.getBorrowDate(), formatter);
                LocalDate currentDate = LocalDate.now();
                long borrowedDays = ChronoUnit.DAYS.between(borrowedDate, currentDate);
                System.out.println("Name: " + student.getName() + ", Enrollment: " + student.getEnrollment() + 
                    ", Book Title: " + student.getBookTitle() + ", Borrowed  " + borrowedDays + " days ago");
            }
        }catch(Exception e){
            System.out.println("Error retrieving data: " + e.getMessage());
        }
    }

    public static void handleAdminEvents(){
        while(true){
            System.out.println("\n--- Admin Menu ---");
            System.out.println("[ADMIN] 1. View All Books");
            System.out.println("[ADMIN] 2. Add books to library");
            System.out.println("[ADMIN] 3. View Students");
            System.out.println("[ADMIN] 4. View Students Who Didn't Return Books");
            System.out.println("[ADMIN] 5. Logout");
            System.out.print("[ADMIN] Enter Your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllBooks();
                    break;
                case "2":
                    addBooksToLibrary();
                    break;
                case "3":
                    viewStudents();
                    break;
                case "4":
                    viewStudentsWhoDidntReturnBooks();
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

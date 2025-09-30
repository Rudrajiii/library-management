package library.controller;
import library.dboperations.BooksDAO;
import library.model.Book;
import java.util.Scanner;

public class HandleBooksUnderStudent {
    private static final Scanner scanner = new Scanner(System.in);
    public static void showAllBooks(){
        /*
         * @use will fetch all books from 'books' table
         */
        System.out.println("Books Available in Library:\nBooks Id / Book Name / Availability");
        BooksDAO bookDAO = new BooksDAO();
        Book[] books = bookDAO.getAllBooks();
        
        if(books == null || books.length == 0){
            System.out.println("No books available in the library.");
            return;
        }
        
        for(Book book : books){
            System.out.println(book);
        }
    }
    public static void handleBookEvents(){
        /*
         * @use will handle all book related events for student
         */
        System.out.println("1. View All Books");
        System.out.println("2. Books Under You");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                showAllBooks();
                break;
            
            case 2:
                // handleBooksUnderYou();
                break;
            case 3:
                // handleBorrowBook();
                break;
            case 4: 
                // handleReturnBook();
                break;
            default:
                return;
        }
    }
}

package library.controller;

import library.dboperations.BorrowedBooksDAO;
import library.model.Book;
import library.model.BorrowedBookModel;
import library.services.BookService;
import library.dboperations.StudentDAO;
import java.util.Scanner;

public class HandleBooksUnderStudent {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAllBooks() {
        /*
         * @use will fetch all books from 'books' table
         */
        System.out.println("Books Available in Library:\n\nBooks Id / Book Name / Availability");
        try {
            BookService bookService = new BookService();
            Book[] books = bookService.getAllBooks();

            if (books == null || books.length == 0) {
                System.out.println("No books available in the library.");
                return;
            }

            for (Book book : books) {
                System.out.println(book);
            }
        } catch (Exception e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
    }

    private static void showBooksUnderYou() {
        /* show all borrowed books for the logged-in student */
        BorrowedBooksDAO borrowedBooksDAO = new BorrowedBooksDAO();
        /* get student id by enrollment number */
        StudentDAO studentDAO = new StudentDAO();
        System.out.println("Enter your enrollment number: ");
        String enrollmentNumber = scanner.nextLine().trim();

        Integer studentId = studentDAO.getStudentId(enrollmentNumber);
        if (studentId == null) {
            System.out.println("Invalid enrollment number.");
            return;
        }

        BorrowedBookModel[] borrowedBooks = borrowedBooksDAO.getBorrowedBooksByStudentId(studentId);

        if (borrowedBooks == null || borrowedBooks.length == 0) {
            System.out.println("You have not borrowed any books.");
            return;
        }
        System.out.println("Books under you:\nBook Id / Book Name / Borrow Date / Return Date / Is Returned / Fine");
        BookService bookService = new BookService();
        for (BorrowedBookModel borrowedBook : borrowedBooks) {
            try {
                String bookName = bookService.getBookName(borrowedBook.getBookId());
                System.out.println(
                        borrowedBook.getBookId() + " / " +
                                bookName + " / " +
                                borrowedBook.getBorrowDate() + " / " +
                                borrowedBook.getReturnDate() + " / " +
                                (borrowedBook.isReturned() ? "Yes" : "No") + " / " +
                                borrowedBook.getFine());
            } catch (Exception e) {
                System.out.println("Error fetching book name: " + e.getMessage());
                continue;
            }
        }
    }

    private static void handleBorrowBook() {
        /*
         * @use handle borrowing logic
         */
        while (true) {
            System.out.println("\n--- Borrow Book ---");
            System.out.print("Enter Book ID to borrow (0 to cancel): ");
            try {
                int bookId = Integer.parseInt(scanner.nextLine().trim());

                if (bookId == 0) {
                    System.out.println("Borrowing cancelled.");
                    return;
                }
                /* check if this book is available */
                // BooksDAO bookDAO = new BooksDAO();
                BookService bookService = new BookService();
                String bookName = bookService.getBook(bookId);
                if (bookName.equals("NOT_AVAILABLE")) {
                    System.out.println("Book is not available for borrowing.\nPlease choose another book.");
                    continue;
                }
                if (bookName.equals("INVALID_BOOK_ID")) {
                    System.out.println("Invalid Book ID. Please try again.");
                    continue;
                }

                /* now borrow the book */
                /* ask for student enrollment number */
                System.out.print("Enter your enrollment number: ");
                String enrollmentNumber = scanner.nextLine().trim();
                /* now get the student id */
                StudentDAO studentDAO = new StudentDAO();
                Integer studentId = studentDAO.getStudentId(enrollmentNumber);
                if (studentId == null) {
                    System.out.println("Invalid enrollment number.");
                    continue;
                }

                /* now set the borrowed book details */
                BorrowedBooksDAO borrowedBooksDAO = new BorrowedBooksDAO();
                BorrowedBookModel borrowedBook = new BorrowedBookModel(
                        0,
                        studentId,
                        bookId,
                        java.time.LocalDate.now().toString(),
                        java.time.LocalDate.now().plusDays(14).toString(), // return date after 14 days
                        false,
                        0.0 // no fine when borrowing
                );

                boolean inserted = borrowedBooksDAO.addBorrowedBook(borrowedBook);

                if (inserted) {
                    System.out.println("Book " + bookName + " borrowed successfully.");
                    /* now also decrement the available copies of the book */
                    bookService.decrementBookAvailability(bookId);
                } else {
                    System.out.println("Failed to record borrowing. Please try again.");
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid Book ID.");
                continue;
            }
        }

    }

    private static void handleReturnBook() {
        /*
         * @use handle returning logic with fine calculation
         */
        BorrowedBooksDAO borrowedBooksDAO = new BorrowedBooksDAO();
        StudentDAO studentDAO = new StudentDAO();

        System.out.print("Enter your enrollment number: ");
        String enrollmentNumber = scanner.nextLine().trim();

        Integer studentId = studentDAO.getStudentId(enrollmentNumber);
        if (studentId == null) {
            System.out.println("Invalid enrollment number.");
            return;
        }

        // Show borrowed books that are not yet returned
        BorrowedBookModel[] borrowedBooks = borrowedBooksDAO.getBorrowedBooksByStudentId(studentId);

        if (borrowedBooks == null || borrowedBooks.length == 0) {
            System.out.println("You have not borrowed any books.");
            return;
        }

        boolean hasUnreturnedBooks = false;

        // BooksDAO bookDAO = new BooksDAO();
        System.out.println("Your unreturned books:\nBorrowed Book ID / Book ID / Borrow Date / Return Date / Fine");
        BookService bookService = new BookService();
        for (BorrowedBookModel borrowedBook : borrowedBooks) {
            if (!borrowedBook.isReturned()) {
                hasUnreturnedBooks = true;
                try {
                    String bookName = bookService.getBookName(borrowedBook.getBookId());
                    System.out.println(
                            borrowedBook.getId() + " / " +
                                    borrowedBook.getBookId() + " (" + bookName + ") / " +
                                    borrowedBook.getBorrowDate() + " / " +
                                    borrowedBook.getReturnDate() + " / " +
                                    borrowedBook.getFine());
                } catch (Exception e) {
                    System.out.println("Error fetching book name: " + e.getMessage());
                    continue;
                }
            }
        }

        if (!hasUnreturnedBooks) {
            System.out.println("All your borrowed books have been returned.");
            return;
        }

        System.out.print("\nEnter Borrowed Book ID to return (0 to cancel): ");
        try {
            int borrowedBookId = Integer.parseInt(scanner.nextLine().trim());

            if (borrowedBookId == 0) {
                System.out.println("Return cancelled.");
                return;
            }

            // Verify this borrowed book belongs to the student
            boolean validBorrowedBook = false;
            int bookId = 0;
            for (BorrowedBookModel borrowedBook : borrowedBooks) {
                if (borrowedBook.getId() == borrowedBookId && !borrowedBook.isReturned()) {
                    validBorrowedBook = true;
                    bookId = borrowedBook.getBookId();
                    break;
                }
            }

            if (!validBorrowedBook) {
                System.out.println("Invalid Borrowed Book ID or book already returned.");
                return;
            }

            // Return the book (this will calculate and store fine if overdue)
            boolean returned = borrowedBooksDAO.returnBook(borrowedBookId);
            if (returned) {
                // Increment book availability
                bookService.incrementBookAvailability(bookId);
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid Borrowed Book ID.");
        }
    }

    public static void handleBookEvents() {
        /*
         * @use will handle all book related events for student
         */
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("[STUDETNT] 1. View All Books");
            System.out.println("[STUDENT] 2. Books Under You");
            System.out.println("[STUDENT] 3. Borrow Book");
            System.out.println("[STUDENT] 4. Return Book");
            System.out.println("[STUDENT] 5. Logout");
            System.out.print(" [STUDENT] Enter Your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    showAllBooks();
                    break;
                case 2:
                    showBooksUnderYou();
                    break;
                case 3:
                    handleBorrowBook();
                    break;
                case 4:
                    handleReturnBook();
                    break;
                case 5:
                    // return to main menu
                    System.out.println("[STUDENT] Logging out...");
                    return;
                default:
                    System.out.println("[STUDENT] Invalid choice. Please try again.");
                    continue;
            }
        }
    }
}

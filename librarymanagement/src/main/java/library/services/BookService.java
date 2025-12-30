package library.services;
import library.model.Book;

import java.sql.SQLException;

import library.dboperations.BooksDAO;

public class BookService {
    private final BooksDAO booksDAO = new BooksDAO();

    public void addBook(Book book) throws SQLException {
        booksDAO.addBook(book);
    }

    public Book getBookByBookId(int bookId) throws SQLException {
        return booksDAO.findBookByBookId(bookId);
    }

    public String getBook(int bookId) throws SQLException {
        return booksDAO.findBook(bookId);
    }

    public String getBookName(int bookId) throws SQLException {
        return booksDAO.findBookName(bookId);
    }

    public void incrementBookAvailability(int bookId)  throws SQLException {
        int affectedRows = booksDAO.incrementBookAvailability(bookId);
        if(affectedRows > 0){
                System.out.println(">> Book availability incremented. <<");
        } else {
                System.out.println(">> Book availability could not be incremented. <<");
        }
    }

    public void decrementBookAvailability(int bookId)  throws SQLException {
        int affectedRows = booksDAO.decrementBookAvailability(bookId);
        if(affectedRows > 0){
                System.out.println(">> Book availability decremented. <<");
        } else {
                System.out.println(">> Book availability could not be decremented (maybe no copies available). <<");
        }
    }

    public Book[] getAllBooks() throws SQLException {
        return booksDAO.findAllBooks();
    }
}

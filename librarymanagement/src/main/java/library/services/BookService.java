package library.services;
import library.model.Book;

import java.sql.SQLException;

import library.dboperations.BooksDAO;

public class BookService {
    private final BooksDAO booksDAO = new BooksDAO();

    public void addBook(Book book) throws SQLException {
        booksDAO.addBook(book);
    }
}

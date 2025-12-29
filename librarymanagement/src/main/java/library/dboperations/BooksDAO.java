package library.dboperations;

import library.model.Book;
import library.utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksDAO {
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books(bookName , available) VALUES(?,?)";

        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                sql , Statement.RETURN_GENERATED_KEYS
            );
        ){
            pstmt.setString(1, book.getBookName());
            pstmt.setInt(2, book.getAvailability());
            pstmt.executeUpdate();

            /**
             * @param automatically set the id to a new book
            */
            try(ResultSet keys = pstmt.getGeneratedKeys()){
                if(keys.next()){
                    book.setId(keys.getInt(1));
                }
            }
        }
    }

    public Book getBookByBookId(int bookId){
        String sql = "SELECT * FROM books WHERE id = ?";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return new Book(
                    rs.getInt("id"),
                    rs.getString("bookName"),
                    rs.getInt("available")
                );
            }
        }catch( Exception error){
            error.printStackTrace();
        }
        return null;
    }

    public String getBook(int bookId){
        String sql = "SELECT * FROM books WHERE id = ?";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Boolean isAvailable = rs.getInt("available") > 0;
                return isAvailable ? rs.getString("bookName") : "NOT_AVAILABLE";
            }
            // if no such book id found
            return "INVALID_BOOK_ID";
        }catch( Exception error){
            error.printStackTrace();
        }
        return "NOT_AVAILABLE";
    }

    public String getBookName(int bookId){
        String sql = "SELECT bookName FROM books WHERE id = ?";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return rs.getString("bookName");
            }
        }catch( Exception error){
            error.printStackTrace();
        }
        return null;
    }

    public void decrementBookAvailability(int bookId){
        String sql = "UPDATE books SET available = available - 1 WHERE id = ? AND available > 0";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, bookId);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                System.out.println(">> Book availability decremented. <<");
            } else {
                System.out.println(">> Book availability could not be decremented (maybe no copies available). <<");
            }
        }catch( Exception error){
            error.printStackTrace();
        }
    }

    public void incrementBookAvailability(int bookId){
        String sql = "UPDATE books SET available = available + 1 WHERE id = ?";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, bookId);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                System.out.println(">> Book availability incremented. <<");
            } else {
                System.out.println(">> Book availability could not be incremented. <<");
            }
        }catch( Exception error){
            error.printStackTrace();
        }
    }

    public Book[] getAllBooks(){
        String sql = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        
        try(
            Connection conn = DBUtil.getConnection();
            Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("bookName"),
                    rs.getInt("available")
                );

                bookList.add(book);
            }
        }catch( Exception error){
            error.printStackTrace();
        }
        
        return bookList.toArray(new Book[0]);
    }
}

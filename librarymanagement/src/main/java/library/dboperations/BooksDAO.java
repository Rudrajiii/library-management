package library.dboperations;

import library.model.Book;
import library.utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksDAO {
    public void addBook(Book book){
        String sql = "INSERT INTO books(bookName , available) VALUES(?,?)";

        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, book.getBookName());
            pstmt.setInt(2, book.getAvailability());
            pstmt.executeUpdate();

            /**
             * @param automatically set the id to a new student
            */
            try(ResultSet getGeneratedkeys = pstmt.getGeneratedKeys()){
                if(getGeneratedkeys.next()){
                    book.setId(getGeneratedkeys.getInt(1));
                }
            }

            System.out.println(">> Book added successfully <<");
        }catch ( Exception error){
            error.printStackTrace();
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

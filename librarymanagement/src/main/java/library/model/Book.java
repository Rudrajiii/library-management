package library.model;

public class Book {
    private int id;
    private String bookName;
    private int available;

    public Book(String bookName , int available){
        this.bookName = bookName;
        this.available = available;
    }

    public Book(int id , String bookName , int available){
        this.id = id;
        this.bookName = bookName;
        this.available = available;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(int id){
        return id;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    public String getBookName(){
        return bookName;
    }

    public void setAvailablity(int num){
        this.available = num;
    }

    public int getAvailability(){
        return available;
    }

    @Override
    public String toString() {
        return id + " / " + bookName + " / " + available;
    }
}

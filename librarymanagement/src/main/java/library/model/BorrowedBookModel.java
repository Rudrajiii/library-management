package library.model;

public class BorrowedBookModel {
    private int id;
    private int studentId;
    private int bookId;
    private String borrowDate;
    private String returnDate;
    private boolean isReturned;
    private double fine;

    public BorrowedBookModel(int id, int studentId, int bookId, String borrowDate, String returnDate, boolean isReturned, double fine) {
        this.id = id;
        this.studentId = studentId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.fine = fine;
    }

    public BorrowedBookModel(int studentId, int bookId, String borrowDate, String returnDate, boolean isReturned, double fine) {
        this.studentId = studentId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.fine = fine;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "BorrowedBookModel{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", bookId=" + bookId +
                ", borrowDate='" + borrowDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", isReturned=" + isReturned +
                ", fine=" + fine +
                '}';
    }
}

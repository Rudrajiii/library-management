package library.source;

import library.model.Book;

public class BooksData {
    private Book[] books = {
            new Book("Harry Potter", 5),
            new Book("Lord of the Rings", 2),
            new Book("The Hobbit", 6),
            new Book("The Alchemist", 8)
    };

    public Book[] get_books(){
        return books;
    }

}

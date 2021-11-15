package com.company.Summative2PringleSteven.dao;

import com.company.Summative2PringleSteven.Model.Book;

import java.util.List;

public interface BookDao {

    Book getBook(int bookID);

    List<Book> getAllBooks();

    Book addBook(Book book);

    void updateBook(Book book);

    void deleteBook(int bookID);

    List <Book> getBooksByAuthor(int authorID);
    // List <Book> getBooksByAuthor(String author);

}

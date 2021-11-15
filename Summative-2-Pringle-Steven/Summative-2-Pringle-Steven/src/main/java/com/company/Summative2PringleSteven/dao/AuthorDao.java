package com.company.Summative2PringleSteven.dao;

import com.company.Summative2PringleSteven.Model.Author;

import java.util.List;

public interface AuthorDao {

    Author addAuthor(Author author);

    Author getAuthor (int id);

    List<Author> getAllAuthor();

    void updateAuthor(Author author);

    void deleteAuthor(int id);
}
package com.company.Summative2PringleSteven.dao;

import com.company.Summative2PringleSteven.Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoJDBCTemplate implements BookDao {

    // Statement Strings
    private static final String Insert_Book_SQL =
            "insert into book (isbn, publish_date, author_id, title, publisher_id, price) values (?, ?, ?, ?, ?, ?)";

    private static final String Select_Book_SQL =
            "select * from book where book_id = ?";

    private static final String Select_All_Books_SQL =
            "select * from book";

    private static final String Delete_Book_SQL =
            "delete from book where book_id = ?";

    private static final String Update_Book_SQL =
            "update book set publish_date = ?, isbn = ?, author_id = ?, title = ?, publisher_id = ?, price = ? where book_id = ?";

    private static final String Select_Books_By_Make_SQL =
            "select * from book where author_id = ?";


    private JdbcTemplate jdbcTemplate;



    @Autowired
    public BookDaoJDBCTemplate (JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }




    public Book getBook(int bookID)
    {
        try
        {
            return jdbcTemplate.queryForObject(Select_Book_SQL, this::mapRowToBook, bookID);
        } catch (EmptyResultDataAccessException e)
        {
            // if nothing is returned just catch the exception and return null
            return null;
        }
    }

    public List<Book> getAllBooks()
    {
        return jdbcTemplate.query(Select_All_Books_SQL, this::mapRowToBook);
    }

    public Book addBook(Book book)
    {
        jdbcTemplate.update(Insert_Book_SQL,
                book.getPublishDate(),
                book.getIsbn(),
                book.getAuthorID(),
                book.getPublisherID(),
                book.getPrice(),
                book.getTitle());
        int bookID = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        book.setBookID(bookID);
        return book;
    }

    public void updateBook(Book book)
    {
        jdbcTemplate.update(Update_Book_SQL,
                book.getPublishDate(),
                book.getIsbn(),
                book.getAuthorID(),
                book.getPublisherID(),
                book.getPrice(),
                book.getTitle());
    }

    public void deleteBook(int bookID)
    {
        jdbcTemplate.update(Delete_Book_SQL, bookID);
    }
    //public List <Book> getBooksByAuthor(String author)
    public List <Book> getBooksByAuthor(int authorID)
    {
        return jdbcTemplate.query(Select_Books_By_Make_SQL, this::mapRowToBook, authorID);
    }



    //row mapper
    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException
    {
        Book book = new Book();
        book.setBookID(rs.getInt("book_id"));
        book.setIsbn(rs.getString("isbn"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setPublisherID(rs.getInt("publisher_id"));
        book.setAuthorID(rs.getInt("author_id"));
        book.setPublishDate(rs.getDate("publish_date").toLocalDate());
        book.setTitle(rs.getString("title"));

        return book;
    }
}

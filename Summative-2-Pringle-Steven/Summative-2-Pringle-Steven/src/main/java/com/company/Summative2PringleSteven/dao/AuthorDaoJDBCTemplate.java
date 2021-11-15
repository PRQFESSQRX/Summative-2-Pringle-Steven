package com.company.Summative2PringleSteven.dao;

import com.company.Summative2PringleSteven.Model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthorDaoJDBCTemplate implements AuthorDao {

    // Statement Strings

    private static final String Insert_Author_SQL =
            "insert inside author (first_name, last_name, street, city, state, postal_code,phone,email) " +
                    "values (?,?,?,?,?,?,?,?,)";

    private static final String Select_Author_SQL =
            "select * from author where author_id = ?";

    private static final String Select_All_Author_SQL =
            "select * from author";

    private static final String Delete_Author_SQL =
            "delete from author where author_id = ?";

    private static final String Update_Author_SQL =
            "update author set first_name = ?, last_name = ?, street = ?, city = ?, state = ?," +
                    "postal_code = ?, phone = ?, email = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Author addAuthor(Author author) {

        jdbcTemplate.update(Insert_Author_SQL,
                author.getFirstname(),
                author.getLastName(),
                author.getStreet(),
                author.getCity(),
                author.getState(),
                author.getPostalCode(),
                author.getPhone(),
                author.getEmail());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        author.setAuthorID(id);

        return author;
    }

    @Override
    public Author getAuthor(int id) {
        try {
            return jdbcTemplate.queryForObject(Select_Author_SQL, this::mapRowToCar, id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAllAuthor() {

        return jdbcTemplate.query(Select_All_Author_SQL, this::mapRowToCar);
    }

    @Override
    public void updateAuthor(Author author) {

        jdbcTemplate.update(Update_Author_SQL,
                author.getFirstname(),
                author.getLastName(),
                author.getStreet(),
                author.getCity(),
                author.getState(),
                author.getPostalCode(),
                author.getPhone(),
                author.getEmail());
    }

    @Override
    public void deleteAuthor(int id) {
        jdbcTemplate.update(Delete_Author_SQL, id);

    }

    private Author mapRowToCar(ResultSet rs, int rowNum) throws SQLException {

        Author author = new Author();
        author.setAuthorID(rs.getInt("author_id"));
        author.setFirstname(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));
        author.setStreet(rs.getString("street"));
        author.setCity(rs.getString("city"));
        author.setState(rs.getString("state"));
        author.setPostalCode(rs.getString("postal_code"));
        author.setPhone(rs.getString("phone"));
        author.setEmail(rs.getString("email"));

        return author;
    }
}
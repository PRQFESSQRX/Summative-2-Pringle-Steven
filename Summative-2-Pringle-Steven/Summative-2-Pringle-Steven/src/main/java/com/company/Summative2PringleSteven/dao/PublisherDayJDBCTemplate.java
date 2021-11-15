package com.company.Summative2PringleSteven.dao;

import com.company.Summative2PringleSteven.Model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Repository
public class PublisherDayJDBCTemplate implements PublisherDao{

    private JdbcTemplate jdbcTemplate;

    // Statement Strings

    private static final String Insert_Publisher_SQL =
            "insert into publisher (name, street, city, state, postal_code, phone, email) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String Select_Publisher_SQL =
            "select * from publisher where publisher_id = ?";

    private static final String Select_All_Publisher_SQL =
            "select * from publisher";

    private static final String Update_Publisher_SQL =
            "update track set name = ?, street = ?, city = ?, state = ?, postal_code = ?, phone = ?, email = ?  where publisher_id = ?";

    private static final String Delete_Publisher_SQL =
            "delete from publisher where publisher_id =  ?";

    @Autowired
    public PublisherDayJDBCTemplate(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    @Transactional
    public Publisher addPublisher(Publisher publisher){

        jdbcTemplate.update(
                Insert_Publisher_SQL,
                publisher.getName(),
                publisher.getStreet(),
                publisher.getCity(),
                publisher.getState(),
                publisher.getPostalCode(),
                publisher.getPhone(),
                publisher.getEmail());

        int id = jdbcTemplate.queryForObject("select Last_Insert_Id()", Integer.class);

        publisher.setId(id);

        return publisher;

    }

    @Override
    public Publisher getPublisher(int id){

        try {
            return jdbcTemplate.queryForObject(
                    Select_Publisher_SQL,
                    this::mapRowToPublisher,
                    id);
        } catch (EmptyResultDataAccessException e) {
            // if there is no entry with the given id, just return null
            return null;
        }
    }

    @Override
    public List<Publisher> getAllPublishers(){

        return jdbcTemplate.query(
                Select_All_Publisher_SQL,
                this::mapRowToPublisher);
    }

    @Override
    public void updatePublisher(Publisher publisher){

        jdbcTemplate.update(
                Update_Publisher_SQL,
                publisher.getName(),
                publisher.getStreet(),
                publisher.getCity(),
                publisher.getState(),
                publisher.getPostalCode(),
                publisher.getPhone(),
                publisher.getEmail());
    }

    @Override
    public void deletePublisher(int id){

        jdbcTemplate.update(Delete_Publisher_SQL, id);
    }

    private Publisher mapRowToPublisher(ResultSet rs, int rowNum) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setId(rs.getInt("publisher_id"));
        publisher.setName(rs.getString("name"));
        publisher.setStreet(rs.getString("street"));
        publisher.setCity(rs.getString("city"));
        publisher.setState(rs.getString("state"));
        publisher.setPostalCode(rs.getString("postal_code"));
        publisher.setPhone(rs.getString("phone"));
        publisher.setEmail(rs.getString("email"));

        return publisher;
    }
}

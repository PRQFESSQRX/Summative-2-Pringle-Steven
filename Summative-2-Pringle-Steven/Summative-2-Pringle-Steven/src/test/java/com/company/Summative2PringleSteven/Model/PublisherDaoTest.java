package com.company.Summative2PringleSteven.Model;

import com.company.Summative2PringleSteven.dao.AuthorDao;
import com.company.Summative2PringleSteven.dao.BookDao;
import com.company.Summative2PringleSteven.dao.PublisherDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PublisherDaoTest {
    @Autowired
    AuthorDao authorDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    PublisherDao publisherDao;
    @Before
    public void setUp() throws Exception{

        // DB Scrubbing

        List<Book> BookList = bookDao.getAllBooks();
        for (Book B : BookList){
            bookDao.deleteBook(B.getBookID());
        }

        List<Publisher> PublisherList = publisherDao.getAllPublishers();
        for (Publisher P : PublisherList){
            publisherDao.deletePublisher(P.getId());
        }

        List<Author> AuthorList = authorDao.getAllAuthor();
        for(Author A : AuthorList){
            authorDao.deleteAuthor(A.getAuthorID());
        }
    }
    @Test
    public void addGetDeletePublisher(){
        Publisher pub = new Publisher();
        pub.setName("PubName");
        pub.setEmail("publish@outlook.com");
        pub.setStreet("Test street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Norfolk");
        pub.setState("VA");
        pub.setPostalCode("55565");
        pub = publisherDao.addPublisher(pub);

        //creates a new publisher and assigns it to current publisher
        Publisher pub1 = publisherDao.getPublisher(pub.getId());

        //Assert
        assertEquals(pub1, pub);

        //deletes recently added publisher
        publisherDao.deletePublisher(pub.getId());

        //assigns publisher after deletion of publisher1
        pub1 = publisherDao.getPublisher(pub.getId());

        //Assert
        assertNull(pub1);
    }

    @Test
    public void getAllPublishers(){

        //Adds first publisher
        Publisher pub = new Publisher();
        pub.setName("PubName");
        pub.setEmail("publish@outlook.com");
        pub.setStreet("Test street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Norfolk");
        pub.setState("VA");
        pub.setPostalCode("55565");
        pub = publisherDao.addPublisher(pub);

        pub = new Publisher();
        pub.setName("PublisherTest2");
        pub.setEmail("publish2@outlook.com");
        pub.setStreet("Test2 street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Washington");
        pub.setState("DC");
        pub.setPostalCode("99898");
        pub = publisherDao.addPublisher(pub);

        //gets all publishers and adds to new list
        List<Publisher> pList = publisherDao.getAllPublishers();

        //Assert
        assertEquals(pList.size(), 2);
    }

    @Test
    public void updatePublisher(){

        Publisher pub = new Publisher();
        pub.setName("PubName");
        pub.setEmail("publish@outlook.com");
        pub.setStreet("Test street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Norfolk");
        pub.setState("VA");
        pub.setPostalCode("55565");
        pub = publisherDao.addPublisher(pub);
        pub = publisherDao.addPublisher(pub);

        pub.setName("NewPublisher");
        pub.setEmail("newpublisheremail@gmail.com");
        pub.setPhone("3345786547");

        publisherDao.updatePublisher(pub);

        Publisher publisher1 = publisherDao.getPublisher(pub.getId());

        //Assert
        assertEquals(publisher1, pub);
    }
}

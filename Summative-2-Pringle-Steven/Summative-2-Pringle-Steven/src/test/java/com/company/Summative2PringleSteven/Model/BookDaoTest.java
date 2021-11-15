package com.company.Summative2PringleSteven.Model;

import com.company.Summative2PringleSteven.dao.AuthorDao;
import com.company.Summative2PringleSteven.dao.BookDao;
import com.company.Summative2PringleSteven.dao.PublisherDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoTest {
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
    public void addGetDeleteBook(){

        //Arrange
        Author authr = new Author();
        authr.setFirstname("UpdateFirst");
        authr.setLastName("UpdateLast");
        authr.setEmail("Update@outlook.com");
        authr.setPhone("XXX-XXX-XXXX");
        authr.setCity("Norfolk");
        authr.setState("VA");
        authr.setStreet("Update Street");
        authr.setPostalCode("00000");
        authr = authorDao.addAuthor(authr);

        Publisher pub = new Publisher();
        pub.setName("PubName");
        pub.setEmail("publish@outlook.com");
        pub.setStreet("Test street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Norfolk");
        pub.setState("VA");
        pub.setPostalCode("55565");
        pub = publisherDao.addPublisher(pub);

        Book book = new Book();
        book.setIsbn("Test####");
        book.setPublishDate(LocalDate.of(2021, 1, 29));
        book.setAuthorID(authr.getAuthorID());
        book.setTitle("testBook");
        book.setPublisherID(pub.getId());
        book.setPrice(new BigDecimal("19.11"));
        book = bookDao.addBook(book);
        Book book1 = bookDao.getBook(book.getBookID());
        // creates book and assigns id

        //assert
        assertEquals(book1, book);
        bookDao.deleteBook(book.getBookID());

        //deletes books just added
        book1 = bookDao.getBook(book.getBookID());

        //assert
        assertNull(book1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    //throws an exception if  the one to one mapping is broken
    public void addWithRefIntegrityException(){

        Book book = new Book();
        book.setIsbn("TEST####");
        book.setPublishDate(LocalDate.of(2021, 1, 29));
        book.setAuthorID(6);
        book.setTitle("testBook");
        book.setPublisherID(12);
        book.setPrice(new BigDecimal("19.11"));

        book = bookDao.addBook(book);

    }

    @Test
    public void getAllBooks() {

        //Need to create an author and publisher first
        Author authr = new Author();
        authr.setFirstname("UpdateFirst");
        authr.setLastName("UpdateLast");
        authr.setEmail("Updatelast@outlook.com");
        authr.setPhone("XXX-XXX-XXXX");
        authr.setCity("Norfolk");
        authr.setState("VA");
        authr.setStreet("Update Street");
        authr.setPostalCode("00000");
        authr = authorDao.addAuthor(authr);

        Publisher pub = new Publisher();
        pub.setName("PubName");
        pub.setEmail("publish@outlook.com");
        pub.setStreet("Test street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Norfolk");
        pub.setState("VA");
        pub.setPostalCode("55565");
        pub = publisherDao.addPublisher(pub);

        //add first book

        Book book = new Book();
        book.setIsbn("TEST####");
        book.setPublishDate(LocalDate.of(2021, 1, 29));
        book.setAuthorID(authr.getAuthorID());
        book.setTitle("testBook");
        book.setPublisherID(pub.getId());
        book.setPrice(new BigDecimal("19.11"));

        book = bookDao.addBook(book);

        //add second book
        book = new Book();
        book.setIsbn("TEST####-##");
        book.setPublishDate(LocalDate.of(1999, 1, 29));
        book.setAuthorID(authr.getAuthorID());
        book.setTitle("Jitensha");
        book.setPrice(new BigDecimal("Free.99"));
        book.setPublisherID(pub.getId());
        book = bookDao.addBook(book);

        List<Book> bList = bookDao.getAllBooks();

        assertEquals(bList.size(), 2);
    }

    @Test
    public void getBookByAuthor(){

        //Need to create an author and publisher first
        Author authr = new Author();
        authr.setFirstname("UpdateFirst");
        authr.setLastName("UpdateLast");
        authr.setEmail("Updatelast@outlook.com");
        authr.setPhone("XXX-XXX-XXXX");
        authr.setCity("Norfolk");
        authr.setState("VA");
        authr.setStreet("Update Street");
        authr.setPostalCode("00000");
        authr = authorDao.addAuthor(authr);

        Publisher pub = new Publisher();
        pub.setName("PubName");
        pub.setEmail("publish@outlook.com");
        pub.setStreet("Test street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Norfolk");
        pub.setState("VA");
        pub.setPostalCode("55565");
        pub = publisherDao.addPublisher(pub);

        //add first book
        Book book = new Book();
        book.setTitle("testBook");
        book.setIsbn("TEST####");
        book.setPrice(new BigDecimal("19.11"));
        book.setPublishDate(LocalDate.of(2021, 1, 29));
        book.setAuthorID(authr.getAuthorID());
        book.setPublisherID(pub.getId());
        book = bookDao.addBook(book);

        //add second book
        book = new Book();
        book.setTitle("Jitensha");
        book.setIsbn("TEST####-##");
        book.setPrice(new BigDecimal("Free.99"));
        book.setPublishDate(LocalDate.of(1999, 1, 29));
        book.setAuthorID(authr.getAuthorID());
        book.setPublisherID(pub.getId());
        book = bookDao.addBook(book);

        //Act
        bookDao.addBook(book);
        // List<Book> bList = bookDao.getBooksByAuthor("PublisherTest");

        //Assert
        //assertEquals(bList.size(), 2);

    }

    @Test
    public  void updateBook(){

        //Need to create an author and publisher first
        Author authr = new Author();
        authr.setFirstname("UpdateFirst");
        authr.setLastName("UpdateLast");
        authr.setEmail("Updatelast@outlook.com");
        authr.setPhone("XXX-XXX-XXXX");
        authr.setCity("Norfolk");
        authr.setState("VA");
        authr.setStreet("Update Street");
        authr.setPostalCode("00000");
        authr = authorDao.addAuthor(authr);

        Publisher pub = new Publisher();
        pub.setName("PubName");
        pub.setEmail("publish@outlook.com");
        pub.setStreet("Test street");
        pub.setPhone("XXX-XXX-XXXX");
        pub.setCity("Norfolk");
        pub.setState("VA");
        pub.setPostalCode("55565");
        pub = publisherDao.addPublisher(pub);

        //add first book
        Book book = new Book();
        book.setTitle("testBook");
        book.setIsbn("TEST####");
        book.setPrice(new BigDecimal("19.11"));
        book.setPublishDate(LocalDate.of(2021, 1, 29));
        book.setAuthorID(authr.getAuthorID());
        book.setPublisherID(pub.getId());
        book = bookDao.addBook(book);

        book = bookDao.addBook(book);


        //new information to be updated
        book.setTitle("New Title");
        book.setPublishDate(LocalDate.of(2021, 11, 12));
        book.setPrice(new BigDecimal("70.00"));

        bookDao.updateBook(book);

        Book book1 = bookDao.getBook(book.getBookID());
        assertEquals(book1, book);

    }
}


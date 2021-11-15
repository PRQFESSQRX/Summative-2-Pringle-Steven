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
public class AuthorDaoTest {
    @Autowired
    AuthorDao authorDao;
    @Autowired
    BookDao bookdao;
    @Autowired
    PublisherDao publisherDao;

    @Before
    public void setUp() throws Exception {

        //DB Scrubbing
        List<Author> AuthorList = authorDao.getAllAuthor();
        for (Author a : AuthorList) {
            authorDao.deleteAuthor(a.getAuthorID());
        }

    }

@Test
public void addDeleteauthor() {
    // arangment
    Author authr = new Author();
    authr.setFirstname("UpdateFirst");
    authr.setLastName("UpdateLast");
    authr.setEmail("Update@outlook.com");
    authr.setPhone("XXX-XXX-XXXX");
    authr.setCity("Norfolk");
    authr.setState("VA");
    authr.setStreet("Update Street");
    authr.setPostalCode("00000");
    //act
    authr = authorDao.addAuthor(authr);
    Author authr1 = authorDao.getAuthor(authr.getAuthorID());
    //assert
    assertEquals(authr,authr1);
    //act
    authorDao.deleteAuthor(authr.getAuthorID());
    authr1 = authorDao.getAuthor(authr.getAuthorID());
    //assert
    assertNull(authr1);
}

@Test
public void getAllAuthor(){
        //arrange
    Author authr = new Author();
    authr.setFirstname("Book2First");
    authr.setLastName("Book2Last");
    authr.setEmail("Last_Name@outlook.com");
    authr.setPhone("XXX-XXX-XXXX");
    authr.setCity("Norfolk");
    authr.setState("VA");
    authr.setStreet("700 park Ave");
    authr.setPostalCode("23504");
    //act
    authorDao.addAuthor(authr);
    //arrange
    authr = new Author();
    authr.setFirstname("BookFirstLast");
    authr.setLastName("BookLastLast");
    authr.setEmail("lastlast@gmail.com");
    authr.setPhone("3335555678");
    authr.setCity("Washington");
    authr.setState("DC");
    authr.setStreet("600 road ave");
    authr.setPostalCode("99898");
    //act
    authorDao.addAuthor(authr);
    List<Author> authorList = authorDao.getAllAuthor(); //gets all of the authors
    //assert
    assertEquals(authorList.size(), 2);
}
    @Test
    public void updateAuthors(){
        //arrange
        Author authr = new Author();
        authr.setFirstname("UpdateFirst");
        authr.setLastName("UpdateLast");
        authr.setEmail("Update@outlook.com");
        authr.setPhone("XXX-XXX-XXXX");
        authr.setCity("Fort Mill");
        authr.setState("SC");
        authr.setStreet("Update Street");
        authr.setPostalCode("23093");
        //act
        authr = authorDao.addAuthor(authr);
        authr.setFirstname("Update2First");
        authr.setLastName("Update2Last");
        authr.setEmail("Update2@outlook.com");
        //act
        authorDao.updateAuthor(authr); //updates the DB
        Author authr1 = authorDao.getAuthor(authr.getAuthorID());
        //assert
        assertEquals(authr1, authr);
    }

}


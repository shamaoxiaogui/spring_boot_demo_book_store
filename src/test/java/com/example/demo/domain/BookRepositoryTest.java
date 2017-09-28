package com.example.demo.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@ContextConfiguration(classes = DemoApplication.class, loader = AnnotationConfigContextLoader.class)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findAll() throws Exception {
        Book book1 = new Book().setName("book1");
        Book book2 = new Book().setName("book2");
        List<Book> booksBeforeSave = bookRepository.findAll();
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        assertThat(books.size() - booksBeforeSave.size(), is(2));
    }

    @Test
    public void findOne() throws Exception {
        Book book = new Book().setName("find this");
        bookRepository.save(book);

        Book result = bookRepository.findOne(book.getId());
        assertThat(result, samePropertyValuesAs(book));
    }

    @Test
    public void save() throws Exception {
        Book book = new Book().setDescription("origin");
        bookRepository.save(book);
        assertNotNull(book.getId());
        assertNull(book.getName());

        Book newBook = new Book().setId(book.getId()).setName("new book");
        bookRepository.save(newBook);

        Book newBookFromRepository = bookRepository.findOne(book.getId());
        assertThat(newBookFromRepository, samePropertyValuesAs(newBook));
    }

    @Test
    public void delete() throws Exception {
        Book book = new Book().setName("book to delete");
        bookRepository.save(book);

        bookRepository.delete(book.getId());
        Book result = bookRepository.findOne(book.getId());
        assertNull(result);
    }

}
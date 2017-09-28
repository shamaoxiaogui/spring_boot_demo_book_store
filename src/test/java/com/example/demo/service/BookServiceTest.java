package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    public void listAllBooks() {
        bookService.listAllBooks();
        verify(bookRepository).findAll();
    }

    @Test
    public void getBookById() {
        Book book = new Book().setId(1L).setName("testBook");
        when(bookRepository.findOne(1L)).thenReturn(book);
        when(bookRepository.findOne(2L)).thenReturn(null);

        Book resultBook = bookService.getBookById(1L);
        assertThat(resultBook, samePropertyValuesAs(book));

        resultBook = bookService.getBookById(2L);
        assertNull(resultBook);

        verify(bookRepository, times(2)).findOne(anyLong());
    }

    @Test
    public void createNewBook() throws Exception {
        Book book = new Book();
        bookService.createNewBook(book);
        verify(bookRepository).save(book);
    }

    @Test
    public void updateBook() throws Exception {
        Book originBook = new Book().setId(1L);
        Book newBook = new Book().setId(1L).setName("new one");
        when(bookRepository.findOne(1L)).thenReturn(originBook);
        when(bookRepository.findOne(2L)).thenReturn(null);

        assertTrue(bookService.updateBook(newBook));
        verify(bookRepository).save(newBook);

        assertFalse(bookService.updateBook(newBook.setId(2L)));
    }

    @Test
    public void deleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository).delete(1L);
    }

}
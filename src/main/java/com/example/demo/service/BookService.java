package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findOne(id);
    }

    public void createNewBook(Book book) throws Exception {
        book.setId(null);
        bookRepository.save(book);
    }

    private boolean hasBook(Long id) {
        return bookRepository.findOne(id) != null;
    }

    public boolean updateBook(Book book) throws Exception {
        if (!hasBook(book.getId())) {
            return false;
        }
        bookRepository.save(book);
        return true;
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
}

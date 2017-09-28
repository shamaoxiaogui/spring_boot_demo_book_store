package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.listAllBooks();
    }

    @GetMapping("{id}")
    public ResponseEntity getOneBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return (book != null ?
                new ResponseEntity(book, HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity createBook(@RequestBody Book book) {
        try {
            bookService.createNewBook(book);
            return new ResponseEntity(book, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateBook(@RequestBody Book book) {
        try {
            return (bookService.updateBook(book) ?
                    new ResponseEntity(HttpStatus.NO_CONTENT) :
                    new ResponseEntity(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

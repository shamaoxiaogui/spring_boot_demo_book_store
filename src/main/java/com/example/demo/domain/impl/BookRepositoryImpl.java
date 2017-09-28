package com.example.demo.domain.impl;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class BookRepositoryImpl implements BookRepository {
    private static AtomicLong idCounter = new AtomicLong();
    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book findOne(Long id) {
        return books.get(id);
    }

    @Override
    public Book save(Book book) throws Exception {
        if (book.getId() == null) {
            book.setId(idCounter.incrementAndGet());
        }
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public void delete(Long id) {
        books.remove(id);
    }
}

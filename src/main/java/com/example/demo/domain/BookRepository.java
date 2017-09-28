package com.example.demo.domain;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    Book findOne(Long id);

    Book save(Book book) throws Exception;

    void delete(Long id);
}

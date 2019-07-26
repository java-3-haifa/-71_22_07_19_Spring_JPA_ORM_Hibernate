package com.telran.jpaexample.repository;

import com.telran.jpaexample.repository.entities.BookEntity;

import java.util.Collection;
import java.util.List;

public interface BookRepository {
    boolean save(BookEntity book);
    boolean removeById(long id);
    BookEntity findById(long id);
    List<BookEntity> getAllBooks();
    List<BookEntity> getAllBooksByAuthor(String authorEmail);

    void addAuthor(String email, String name);
}

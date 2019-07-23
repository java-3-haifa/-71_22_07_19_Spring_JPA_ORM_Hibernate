package com.telran.jpaexample.controller;


import com.telran.jpaexample.repository.BookRepository;
import com.telran.jpaexample.repository.entities.AuthorEntity;
import com.telran.jpaexample.repository.entities.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    BookRepository repository;

    @PostMapping()
    public void addBook(@RequestBody BookEntity book){
        repository.save(book);
    }

    @GetMapping("all")
    public List<BookDto> getAllBooks(){
        return repository.getAllBooks().stream().map(this::toBookDto).collect(Collectors.toList());
    }

    @GetMapping("author/{authorEmail}")
    public List<BookDto> getBookByAuthor(@PathVariable("authorEmail")String authorEmail){
        return repository.getAllBooksByAuthor(authorEmail).stream().map(this::toBookDto).collect(Collectors.toList());
    }

    @GetMapping("{bookId}")
    public BookDto getBookById(@PathVariable("bookId")Long bookId){
        return toBookDto(repository.findById(bookId));
    }

    @DeleteMapping("{bookId}")
    public void deleteBookById(@PathVariable("bookId")Long bookId){
        repository.removeById(bookId);
    }

    private BookDto toBookDto(BookEntity entity) {
        BookDto dto = new BookDto();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.authors = entity.getAuthors().stream().map(this::toAuthorDto).collect(Collectors.toList());
        return dto;
    }

    private AuthorDto toAuthorDto(AuthorEntity entity) {
        AuthorDto dto = new AuthorDto();
        dto.email = entity.getEmail();
        dto.name = entity.getName();
        return dto;
    }

}

package com.telran.jpaexample.repository.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "author")
public class AuthorEntity {
    @Id
    String email;
    String name;
    @ManyToMany(mappedBy = "authors",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    Collection<BookEntity> books;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Collection<BookEntity> books) {
        this.books = books;
    }
}

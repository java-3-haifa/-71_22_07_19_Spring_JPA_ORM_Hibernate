package com.telran.jpaexample.repository.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "books")
@NamedQuery(
        name = "getBooksByAuthor",
        query = "select be from BookEntity AS be join AuthorEntity AS a where a.email = :authorEmail"
)
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    Collection<AuthorEntity> authors;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Collection<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(Collection<AuthorEntity> authors) {
        this.authors = authors;
    }
}

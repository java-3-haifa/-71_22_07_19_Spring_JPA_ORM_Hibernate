package com.telran.jpaexample.repository;

import com.telran.jpaexample.repository.entities.AuthorEntity;
import com.telran.jpaexample.repository.entities.BookEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public boolean save(BookEntity book) {
        List<AuthorEntity> list = new ArrayList<>();
        for(AuthorEntity author : book.getAuthors()){
            AuthorEntity a = em.find(AuthorEntity.class,author.getEmail());
            if(a == null){
                throw new RuntimeException("Author does not exist");
            }
            list.add(a);
        }
        book.setAuthors(null);
        em.persist(book);
        book.setAuthors(list);
        return true;
    }

    @Override
    @Transactional
    public boolean removeById(long id) {
        BookEntity res = em.find(BookEntity.class, id);
        if (res != null) {
            em.remove(res);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public BookEntity findById(long id) {
        return em.find(BookEntity.class, id);
    }

    @Override
    @Transactional
    public List<BookEntity> getAllBooks() {
        return em.createQuery("select be from BookEntity AS be", BookEntity.class).getResultList();
    }

    @Override
    @Transactional
    public List<BookEntity> getAllBooksByAuthor(String authorEmail) {
        TypedQuery<BookEntity> query = em.createQuery("select be from BookEntity be join be.authors a where a.email = :authorEmail", BookEntity.class);
        query.setParameter("authorEmail", authorEmail);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void addAuthor(String email, String name) {
        AuthorEntity authorEntity = em.find(AuthorEntity.class,email);
        if(authorEntity == null){
            authorEntity = new AuthorEntity();
            authorEntity.setEmail(email);
            authorEntity.setName(name);
            em.persist(authorEntity);
            return;
        }
        throw new RuntimeException("Author already exist");
    }


//    getAllBooksByAuthor using @NamedQuery
//    @Override
//    public List<BookEntity> getAllBooksByAuthor(String authorEmail) {
//        TypedQuery<BookEntity> query = em.createNamedQuery("getBooksByAuthor", BookEntity.class);
//        query.setParameter("authorEmail", authorEmail);
//        return query.getResultList();
//    }


}

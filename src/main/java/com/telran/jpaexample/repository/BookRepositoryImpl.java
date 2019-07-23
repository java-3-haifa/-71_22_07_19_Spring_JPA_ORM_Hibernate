package com.telran.jpaexample.repository;

import com.telran.jpaexample.repository.entities.AuthorEntity;
import com.telran.jpaexample.repository.entities.BookEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public boolean save(BookEntity book) {
        BookEntity res = null;
        if(book.getId() != null){
            res = em.find(BookEntity.class, book.getId());
        }
        if (res != null) {
            return false;
        }
        em.merge(book);
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
    public BookEntity findById(long id) {
        return em.find(BookEntity.class, id);
    }

    @Override
    @Transactional
    public List<BookEntity> getAllBooks() {
        return em.createQuery("select be from BookEntity AS be", BookEntity.class).getResultList();
    }

    @Override
    public List<BookEntity> getAllBooksByAuthor(String authorEmail) {
        TypedQuery<BookEntity> query = em.createQuery("select be from BookEntity AS be join AuthorEntity AS a where a.email = :authorEmail", BookEntity.class);
        query.setParameter("authorEmail", authorEmail);
        return query.getResultList();
    }


//    getAllBooksByAuthor using @NamedQuery
//    @Override
//    public List<BookEntity> getAllBooksByAuthor(String authorEmail) {
//        TypedQuery<BookEntity> query = em.createNamedQuery("getBooksByAuthor", BookEntity.class);
//        query.setParameter("authorEmail", authorEmail);
//        return query.getResultList();
//    }


}

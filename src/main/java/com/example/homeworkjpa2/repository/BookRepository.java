package com.example.homeworkjpa2.repository;

import com.example.homeworkjpa2.model.Book;
import com.example.homeworkjpa2.model.BookRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;
    public Book getBookByID(UUID id) {
        return entityManager.find(Book.class,id);
    }

    public Book addBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription());
        book.setPublicationYear(bookRequest.getPublicationYear());

        entityManager.persist(book);
        return book;

    }

    public List<Book> getAllBook() {
        String query= "SELECT b FROM Book b";
        Query q = entityManager.createQuery(query, Book.class);
        return q.getResultList();
    }

    public List<Book> getBookByTitle(String title) {
        String q = "SELECT bo FROM Book bo WHERE bo.title ILIKE :title";
        TypedQuery<Book> query = entityManager.createQuery(q, Book.class);
        query.setParameter("title","%" +title+ "%");
        return query.getResultList();
    }

    public Book editBook(UUID id,BookRequest bookRequest) {
        Book book = entityManager.find(Book.class, id);

        entityManager.detach(book);
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription());
        book.setPublicationYear(bookRequest.getPublicationYear());

        entityManager.merge(book);
        return book;
    }

    public Book removeBook(UUID id) {
        Book book = entityManager.find(Book.class, id);
        entityManager.remove(book);
        return book;
    }
}

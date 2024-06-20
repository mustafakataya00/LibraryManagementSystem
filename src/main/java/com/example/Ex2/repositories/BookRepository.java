package com.example.Ex2.repositories;

import com.example.Ex2.Domain.Author;
import com.example.Ex2.Domain.Book;
import com.example.Ex2.Domain.DTO.AuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> searchByTitle(String title);
    boolean existsByTitle(String title);
    List<Book> findByAuthors(Author author);

    List<Book> findByAuthorsName(String authorName);
}


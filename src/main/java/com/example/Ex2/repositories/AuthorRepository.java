package com.example.Ex2.repositories;

import com.example.Ex2.Domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByName(String name);
    boolean existsByName(String name);
}

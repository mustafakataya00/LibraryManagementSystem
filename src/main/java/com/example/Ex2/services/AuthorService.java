package com.example.Ex2.services;

import com.example.Ex2.Domain.Author;
import com.example.Ex2.Domain.DTO.AuthorDTO;
import com.example.Ex2.ErrorHandlers.AlreadyExistsException;
import com.example.Ex2.ErrorHandlers.NotFoundException;
import com.example.Ex2.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author createAuthor(Author newAuthor) {
        if (authorRepository.existsByName(newAuthor.getName())) {
            throw new AlreadyExistsException("Task with title '" + newAuthor.getName() + "' already exists.");
        }
        return authorRepository.save(newAuthor);
    }

    public void deleteAuthor(int id){
        if (!authorRepository.existsById(id)) {
            throw new NotFoundException("Author with id '" + id + "' does not exists.");
        }
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(int id, Author updatedAuthor) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setName(updatedAuthor.getName());
            return authorRepository.save(author);
        } else {
            throw new NotFoundException("Author with id " + id + " not found.");
        }
    }
    public Author patchAuthor(int id, Author updatedAuthor) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            if (updatedAuthor.getName() != null) {
                author.setName(updatedAuthor.getName());
            }
            return authorRepository.save(author);
        } else {
            throw new NotFoundException("Author with id " + id + " not found.");
        }
    }
    public Author getAuthorById(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with id " + id));
    }
    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Author not found with name " + name));
    }

}

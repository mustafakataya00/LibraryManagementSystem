package com.example.Ex2.Domain.DTO;

import com.example.Ex2.Domain.Author;

public class AuthorDTO {

    private Integer id;
    private String name;

    // Constructor
    public AuthorDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Static factory method to convert Author entity to AuthorDTO
    public static AuthorDTO fromAuthor(Author author) {
        return new AuthorDTO(author.getId(), author.getName());
    }

    // Getters and setters (if needed)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

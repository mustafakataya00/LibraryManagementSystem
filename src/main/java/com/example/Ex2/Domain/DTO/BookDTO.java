package com.example.Ex2.Domain.DTO;

import java.time.LocalDate;
import java.util.Set;

public class BookDTO {
    private int id;
    private String title;
    private boolean isBorrowed;
    private Set<AuthorDTO> authors;
    private String borrowerName; // New field for borrower's name
    private LocalDate borrowDate;



    // Constructors, getters, and setters

    public BookDTO() {
    }

    public BookDTO(int id, String title, boolean isBorrowed, Set<AuthorDTO> authors, String borrowerName , LocalDate borrowDate) {
        this.id = id;
        this.title = title;
        this.isBorrowed = isBorrowed;
        this.authors = authors;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;

    }
    public LocalDate getborrowDate() {
        return borrowDate;
    }

    public void setborrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }
}

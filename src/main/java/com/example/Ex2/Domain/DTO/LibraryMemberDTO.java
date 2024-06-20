package com.example.Ex2.Domain.DTO;

import java.util.List;

public class LibraryMemberDTO {
    private int id;
    private String name;
    private List<BookDTO> borrowedBooks;
    private double fine;
    // Constructors, getters, and setters

    public LibraryMemberDTO() {
    }

    public LibraryMemberDTO(int id, String name, List<BookDTO> borrowedBooks , double fine) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = borrowedBooks;
        this.fine = fine;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookDTO> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BookDTO> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

}

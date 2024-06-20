package com.example.Ex2.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LibraryMembers")
public class LibraryMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Member name is required")
    @Size(max = 255)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> borrowedBooks = new HashSet<>();
    private double fine;
    // Constructors, getters, setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public double calculateFine(LocalDate currentDate) {
        double totalFine = 0;
        for (Book book : borrowedBooks) {
//            book.getborrowDate().plusDays(7).isBefore(currentDate)
            if (book.getborrowDate().isBefore(currentDate.plusDays(2))) {
                long daysOverdue = currentDate.plusDays(2).toEpochDay() - book.getborrowDate().toEpochDay();
                totalFine += daysOverdue * 5; // $5 per day overdue
            }
        }
        return totalFine;
    }
}
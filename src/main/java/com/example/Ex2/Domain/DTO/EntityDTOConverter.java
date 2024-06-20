package com.example.Ex2.Domain.DTO;
import com.example.Ex2.Domain.Author;
import com.example.Ex2.Domain.Book;
import com.example.Ex2.Domain.LibraryMember;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class EntityDTOConverter {

    public static BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setBorrowed(book.isBorrowed());
        if (book.isBorrowed() && book.getBorrower() != null) {
            bookDTO.setBorrowerName(book.getBorrower().getName());
        }
        bookDTO.setborrowDate(book.getborrowDate());
        // Convert authors to DTOs
        bookDTO.setAuthors(book.getAuthors().stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toSet()));

        return bookDTO;
    }

    public static LibraryMemberDTO convertMemberToDTO(LibraryMember member) {
        LibraryMemberDTO memberDTO = new LibraryMemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setName(member.getName());
        memberDTO.setFine(member.getFine());
        // Convert borrowed books to DTOs
        memberDTO.setBorrowedBooks(member.getBorrowedBooks().stream()
                .map(EntityDTOConverter::convertBookToDTO)
                .collect(Collectors.toList()));
        return memberDTO;
    }
}

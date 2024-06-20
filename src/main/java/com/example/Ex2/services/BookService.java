package com.example.Ex2.services;

import com.example.Ex2.Domain.Author;
import com.example.Ex2.Domain.Book;
import com.example.Ex2.Domain.DTO.BookDTO;
import com.example.Ex2.Domain.DTO.EntityDTOConverter;
import com.example.Ex2.Domain.LibraryMember;
import com.example.Ex2.ErrorHandlers.AlreadyExistsException;
import com.example.Ex2.ErrorHandlers.NotFoundException;
import com.example.Ex2.repositories.AuthorRepository;
import com.example.Ex2.repositories.BookRepository;
import com.example.Ex2.repositories.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private LibraryMemberRepository memberRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository , LibraryMemberRepository memberRepository , AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(EntityDTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }
    public BookDTO getBookById(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + id));
        return EntityDTOConverter.convertBookToDTO(book);
    }

    public List<BookDTO> searchByTitle(String name) {
        List<Book> books = bookRepository.searchByTitle(name);
        return books.stream()
                .map(EntityDTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getBooksByAuthor(Author author) {
        List<Book> books = bookRepository.findByAuthors(author);
        return books.stream()
                .map(EntityDTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }


    public Book createBook(Book newBook) {
        if (bookRepository.existsByTitle(newBook.getTitle())) {
            throw new AlreadyExistsException("Book with title '" + newBook.getTitle() + "' already exists.");
        }
        // Check if all authors of the new book exist in the database
        for (Author author : newBook.getAuthors()) {
            if (!authorRepository.existsById(author.getId())) {
                // If an author doesn't exist, you may throw an exception or handle it accordingly

                //i could create it whenever author isnt available , but i chose to throw an error
                // authorRepository.save(author);
                throw new NotFoundException("Author with ID '" + author.getId() + "' does not exist.");
            }
        }
        return bookRepository.save(newBook);
    }

    public void deleteBook(int id){
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book with id '" + id + "' does not exists.");
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book task = optionalBook.get();
            task.setTitle(updatedBook.getTitle());
            return bookRepository.save(task);
        } else {
            throw new NotFoundException("Book with id " + id + " not found.");
        }
    }
    public Book patchBook(int id, Book updatedBook) {
        Optional<Book> optionalTask = bookRepository.findById(id);
        if (optionalTask.isPresent()) {
            Book task = optionalTask.get();
            if (updatedBook.getTitle() != null) {
                task.setTitle(updatedBook.getTitle());
            }
            return bookRepository.save(task);
        } else {
            throw new NotFoundException("Book with id " + id + " not found.");
        }
    }


    public BookDTO checkOutBook(LibraryMember member, int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + bookId));

        if (member.getBorrowedBooks().size() >= 5) {
            throw new RuntimeException("Member has borrowed the maximum number of books.");
        }

        if (book.isBorrowed()) {
            throw new RuntimeException("Book is already borrowed.");
        }

        book.setBorrowed(true);
        book.setBorrower(member);
        book.setborrowDate(LocalDate.now());
        member.getBorrowedBooks().add(book);
        bookRepository.save(book);
        memberRepository.save(member);

        // Create BookDTO with borrowerName populated
        BookDTO bookDTO = EntityDTOConverter.convertBookToDTO(book);
        bookDTO.setBorrowerName(member.getName()); // Assuming LibraryMember has a getName() method
        return bookDTO;
    }
    // Method to check in a book from a library member
    public BookDTO checkInBook(LibraryMember member, int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        if (!member.getBorrowedBooks().contains(book)) {
            throw new RuntimeException("Member has not borrowed this book.");
        }
        double fine = member.calculateFine(LocalDate.now());
        book.setBorrowed(false);
        book.setBorrower(null);
        book.setborrowDate(null);
        member.getBorrowedBooks().remove(book);
        member.setFine(fine);
        bookRepository.save(book);
        memberRepository.save(member);

        BookDTO bookDTO = EntityDTOConverter.convertBookToDTO(book);
        bookDTO.setBorrowerName(member.getName()); // Assuming LibraryMember has a getName() method
        return bookDTO;
    }


    public List<BookDTO> getBooksByAuthorName(String authorName) {
        List<Book> books = bookRepository.findByAuthorsName(authorName);
        return books.stream()
                .map(EntityDTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }

}

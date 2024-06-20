package com.example.Ex2.web;

import com.example.Ex2.Domain.Author;
import com.example.Ex2.Domain.Book;
import com.example.Ex2.Domain.DTO.AuthorDTO;
import com.example.Ex2.Domain.DTO.BookDTO;
import com.example.Ex2.ErrorHandlers.ErrorResponse;
import com.example.Ex2.ErrorHandlers.AlreadyExistsException;
import com.example.Ex2.ErrorHandlers.NotFoundException;

import com.example.Ex2.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/BooksAPI")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllbooks() {
        return  bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @GetMapping("/title/{name}")
        public ResponseEntity<?> getBookByTitle(@PathVariable String name) {
        try {
            List<BookDTO> books = bookService.searchByTitle(name);
            return ResponseEntity.ok(books);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody Book newBook , BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors);
        }try {
            Book book = bookService.createBook(newBook);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (AlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * DELETE /BooksAPI/{id} - Delete book by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id){
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book with id " + id + " deleted successfully");
        }
        catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * PUT /BooksAPI/{id} - Update book by id (replace)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePutBook(@PathVariable int id, @RequestBody Book updatedBook) {
        try {
            Book book = bookService.updateBook(id, updatedBook);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    /**
     * PATCH /BooksAPI/{id} - Update book by id (partial update)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePatchBook(@PathVariable int id, @RequestBody Book updatedBook) {
        try {
            Book book = bookService.patchBook(id, updatedBook);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/GetBooksByAuthor")
    public ResponseEntity<?> getBooksByAuthor(@RequestBody Author author) {
        try {
            List<BookDTO> books = bookService.getBooksByAuthor(author);
            return ResponseEntity.ok(books);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/GetBooksByAuthorName/{AuthorName}")
    public ResponseEntity<?> getBooksByAuthorName(@PathVariable String AuthorName) {
        try {
            List<BookDTO> books = bookService.getBooksByAuthorName(AuthorName);
            return ResponseEntity.ok(books);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


}


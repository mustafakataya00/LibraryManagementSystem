package com.example.Ex2.web;

import com.example.Ex2.Domain.Author;
import com.example.Ex2.Domain.Book;
import com.example.Ex2.Domain.DTO.AuthorDTO;
import com.example.Ex2.ErrorHandlers.AlreadyExistsException;
import com.example.Ex2.ErrorHandlers.ErrorResponse;
import com.example.Ex2.ErrorHandlers.NotFoundException;
import com.example.Ex2.services.AuthorService;
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
@RequestMapping(path = "/AuthorAPI")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable int id) {
        try {
            Author author = authorService.getAuthorById(id);
            return ResponseEntity.ok(author);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> getAuthorByName(@PathVariable String name) {
        try {
            Author author = authorService.getAuthorByName(name);
            return ResponseEntity.ok(author);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody Author newAuthor , BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors);
        }try {

            Author author = authorService.createAuthor(newAuthor);

            return ResponseEntity.status(HttpStatus.CREATED).body(author);
        } catch (AlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * DELETE /AuthorAPI/{id} - Delete Author by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id){
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * PUT /AuthorAPI/{id} - Update author by id (replace)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePutAuthor(@PathVariable int id, @RequestBody Author updatedAuthor) {
        try {
            Author author = authorService.updateAuthor(id, updatedAuthor);
            return ResponseEntity.ok(author);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    /**
     * PATCH /AuthorAPI/{id} - Update author by id (partial update)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePatchAuthor(@PathVariable int id, @RequestBody Author updatedAuthor) {
        try {
            Author author = authorService.patchAuthor(id, updatedAuthor);
            return ResponseEntity.ok(author);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}


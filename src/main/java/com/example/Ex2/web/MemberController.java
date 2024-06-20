package com.example.Ex2.web;

import com.example.Ex2.Domain.Book;
import com.example.Ex2.Domain.DTO.BookDTO;
import com.example.Ex2.Domain.DTO.LibraryMemberDTO;
import com.example.Ex2.Domain.LibraryMember;
import com.example.Ex2.ErrorHandlers.NotFoundException;
import com.example.Ex2.services.LibraryMemberService;
import org.hibernate.event.spi.SaveOrUpdateEvent;
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
@RequestMapping(path = "/MemberAPI")
public class MemberController {

    private final LibraryMemberService memberService;

    @Autowired
    public MemberController(LibraryMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<LibraryMemberDTO> getAllMembers() {
        return memberService.getAllLibraryMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable int id) {
        try {
            LibraryMemberDTO member = memberService.getLibraryMemberDTOById(id);
            return ResponseEntity.ok(member);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody LibraryMember member, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors);
        }
        LibraryMember createdMember = memberService.createLibraryMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable int id, @Valid @RequestBody LibraryMember updatedMember,
                                          BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            LibraryMember member = memberService.updateLibraryMember(id, updatedMember);
            if (member != null) {
                return ResponseEntity.ok(member);
            } else {
                throw new NotFoundException("Member not found with id " + id);
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable int id) {
        try {
            memberService.deleteLibraryMember(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{memberId}/checkout/{bookId}")
    public ResponseEntity<?> checkOutBook(@PathVariable int memberId, @PathVariable int bookId) {
        try {
            LibraryMember member = memberService.getLibraryMemberById(memberId);
            BookDTO book = memberService.checkOutBook(member, bookId);
            return ResponseEntity.ok(book);
        } catch ( NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{memberId}/checkin/{bookId}")
    public ResponseEntity<?> checkInBook(@PathVariable int memberId, @PathVariable int bookId) {
        try {
            BookDTO book = memberService.checkInBook(memberId, bookId);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

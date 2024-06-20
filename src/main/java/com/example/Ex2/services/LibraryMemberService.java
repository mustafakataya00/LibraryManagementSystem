package com.example.Ex2.services;

import com.example.Ex2.Domain.Book;
import com.example.Ex2.Domain.DTO.BookDTO;
import com.example.Ex2.Domain.DTO.EntityDTOConverter;
import com.example.Ex2.Domain.DTO.LibraryMemberDTO;
import com.example.Ex2.Domain.LibraryMember;
import com.example.Ex2.ErrorHandlers.NotFoundException;
import com.example.Ex2.repositories.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryMemberService {

    private final LibraryMemberRepository memberRepository;
    private final BookService bookService;

    @Autowired
    public LibraryMemberService(LibraryMemberRepository memberRepository , BookService bookService) {
        this.memberRepository = memberRepository;
        this.bookService = bookService;
    }

    // Create operation
    public LibraryMember createLibraryMember(LibraryMember member) {
        return memberRepository.save(member);
    }

    // Read operation
    public LibraryMember getLibraryMemberById(int id) {
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member not found with id " + id));
    }

    public LibraryMemberDTO getLibraryMemberDTOById(int id) {
        LibraryMember member =  memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member not found with id " + id));
        return EntityDTOConverter.convertMemberToDTO(member);
    }

    public List<LibraryMemberDTO> getAllLibraryMembers() {
        List<LibraryMember> members  = memberRepository.findAll();
        return members.stream()
                .map(EntityDTOConverter::convertMemberToDTO)
                .collect(Collectors.toList());
    }

    // Update operation
    public LibraryMember updateLibraryMember(int id, LibraryMember updatedMember) {
        Optional<LibraryMember> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            updatedMember.setName(updatedMember.getName());
            return memberRepository.save(updatedMember);
        } else {

            return null;
        }
    }

    // Delete operation
    public void deleteLibraryMember(int id) {
        if (!memberRepository.existsById(id)) {
            throw new NotFoundException("Member with id '" + id + "' does not exists.");
        }
        memberRepository.deleteById(id);
    }


    // Method to check out a book for a library member
    public BookDTO checkOutBook(LibraryMember member, int bookId) {
        return bookService.checkOutBook(member, bookId);
    }

    // Method to check in a book from a library member
    public BookDTO checkInBook(int memberId, int bookId) {
        LibraryMember member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Library member not found with id: " + memberId));

        return bookService.checkInBook(member, bookId);
    }

}

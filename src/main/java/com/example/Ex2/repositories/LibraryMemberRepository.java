package com.example.Ex2.repositories;

import com.example.Ex2.Domain.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Integer> {
}

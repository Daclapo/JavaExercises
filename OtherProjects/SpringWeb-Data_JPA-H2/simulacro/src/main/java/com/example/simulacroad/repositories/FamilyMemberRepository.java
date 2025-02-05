package com.example.simulacroad.repositories;

import com.example.simulacroad.entitites.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Integer> {
}

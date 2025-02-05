package com.example.simulacroad.repositories;

import com.example.simulacroad.entitites.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Integer> {
}
package com.example.simulacroad.repositories;

import com.example.simulacroad.entitites.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    public Page<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitle (String title, String description, Pageable pageable);
}
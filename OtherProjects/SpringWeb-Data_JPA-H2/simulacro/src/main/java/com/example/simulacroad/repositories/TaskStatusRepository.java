package com.example.simulacroad.repositories;

import com.example.simulacroad.entitites.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
}
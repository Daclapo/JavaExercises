package com.example.simulacroad.services;


import com.example.simulacroad.dto.NewTaskAssignmentDto;

public interface TaskAssignmentService {
    void CreateTaskAssignment(NewTaskAssignmentDto taskAssignmentDto);

    void CompleteTaskAssignment(Integer taskAssignmentId);

    boolean ExistsById(Integer taskAssignmentId);

    boolean IsCompleted(Integer taskAssignmentId);
}
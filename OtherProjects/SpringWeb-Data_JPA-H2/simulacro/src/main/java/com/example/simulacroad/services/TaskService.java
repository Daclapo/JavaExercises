package com.example.simulacroad.services;

import com.example.simulacroad.entitites.Task;

import java.util.List;

public interface TaskService {
    List<Task> findTasks(String search, int pageNumber, int pageSize);
}
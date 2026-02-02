package com.airin.taskmanager_backend.service;

import java.util.List;

import com.airin.taskmanager_backend.entity.Task;
import com.airin.taskmanager_backend.entity.TaskStatus;

public interface TaskService {
    Task create(Task task);

    List<Task> findAll();
    List<Task> findByUserId(String userId);

    Task findById(String id);

    Task update(String id, Task updated);
    Task updateStatus(String id, TaskStatus status);

    void delete(String id);
}

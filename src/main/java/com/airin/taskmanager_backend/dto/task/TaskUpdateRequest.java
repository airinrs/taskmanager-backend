package com.airin.taskmanager_backend.dto.task;

import java.time.LocalDate;

import com.airin.taskmanager_backend.entity.TaskStatus;

public class TaskUpdateRequest {
    public String title;
    public String description;
    public String userId;
    public TaskStatus status;
    public LocalDate dueDate;
}

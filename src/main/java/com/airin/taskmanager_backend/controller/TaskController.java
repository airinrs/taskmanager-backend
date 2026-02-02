package com.airin.taskmanager_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.airin.taskmanager_backend.dto.task.TaskCreateRequest;
import com.airin.taskmanager_backend.dto.task.TaskResponse;
import com.airin.taskmanager_backend.entity.Task;
import com.airin.taskmanager_backend.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.findAll().stream().map(this::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody TaskCreateRequest req) {
        Task task = new Task(
                null,
                req.title,
                req.description,
                req.userId,
                req.status,
                req.dueDate
        );
        return toResponse(taskService.save(task));
    }

    private TaskResponse toResponse(Task task) {
        TaskResponse res = new TaskResponse();
        res.id = task.getId();
        res.title = task.getTitle();
        res.description = task.getDescription();
        res.userId = task.getUserId();
        res.status = task.getStatus();
        res.dueDate = task.getDueDate();
        return res;
    }
}

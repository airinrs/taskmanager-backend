package com.airin.taskmanager_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.airin.taskmanager_backend.dto.task.TaskCreateRequest;
import com.airin.taskmanager_backend.dto.task.TaskResponse;
import com.airin.taskmanager_backend.dto.task.TaskStatusUpdateRequest;
import com.airin.taskmanager_backend.dto.task.TaskUpdateRequest;
import com.airin.taskmanager_backend.entity.Task;
import com.airin.taskmanager_backend.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks?userId=xxx
    @GetMapping
    public List<TaskResponse> getAll(@RequestParam(required = false) String userId) {
        List<Task> tasks = (userId == null || userId.isBlank())
                ? taskService.findAll()
                : taskService.findByUserId(userId);

        return tasks.stream().map(this::toResponse).toList();
    }

    // GET /api/tasks/{id}
    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable String id) {
        return toResponse(taskService.findById(id));
    }

    // POST /api/tasks
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
        return toResponse(taskService.create(task));
    }

    // PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable String id, @RequestBody TaskUpdateRequest req) {
        Task updated = new Task(
                id,
                req.title,
                req.description,
                req.userId,
                req.status,
                req.dueDate
        );
        return toResponse(taskService.update(id, updated));
    }

    // PATCH /api/tasks/{id}/status
    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable String id, @RequestBody TaskStatusUpdateRequest req) {
        return toResponse(taskService.updateStatus(id, req.status));
    }

    // DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        taskService.delete(id);
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

package com.airin.taskmanager_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.airin.taskmanager_backend.entity.Task;
import com.airin.taskmanager_backend.entity.TaskStatus;
import com.airin.taskmanager_backend.repository.TaskRepository;
import com.airin.taskmanager_backend.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task task) {
        // ensure new doc
        task.setId(null);
        if (task.getStatus() == null) task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByUserId(String userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task findById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    @Override
    public Task update(String id, Task updated) {
        Task existing = findById(id);

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setUserId(updated.getUserId());
        existing.setStatus(updated.getStatus());
        existing.setDueDate(updated.getDueDate());

        return taskRepository.save(existing);
    }

    @Override
    public Task updateStatus(String id, TaskStatus status) {
        Task existing = findById(id);
        existing.setStatus(status);
        return taskRepository.save(existing);
    }

    @Override
    public void delete(String id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found: " + id);
        }
        taskRepository.deleteById(id);
    }
}

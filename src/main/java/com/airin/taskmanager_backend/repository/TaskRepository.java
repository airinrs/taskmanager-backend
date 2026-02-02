package com.airin.taskmanager_backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.airin.taskmanager_backend.entity.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserId(String userId);
}

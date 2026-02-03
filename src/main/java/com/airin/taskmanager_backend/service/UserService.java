package com.airin.taskmanager_backend.service;

import java.util.List;
import java.util.Optional;

import com.airin.taskmanager_backend.entity.User;

public interface UserService {
    User save(User user);
    List<User> findAll();
    Optional<User> findByEmail(String email);
}

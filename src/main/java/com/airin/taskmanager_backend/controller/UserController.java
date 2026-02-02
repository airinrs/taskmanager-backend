package com.airin.taskmanager_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.airin.taskmanager_backend.dto.user.UserRegisterRequest;
import com.airin.taskmanager_backend.dto.user.UserResponse;
import com.airin.taskmanager_backend.entity.User;
import com.airin.taskmanager_backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll().stream().map(this::toResponse).toList();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRegisterRequest req) {
        User user = new User(null, req.email, req.password);
        return toResponse(userService.save(user));
    }

    private UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.id = user.getId();
        res.email = user.getEmail();
        return res;
    }
}

package com.airin.taskmanager_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.airin.taskmanager_backend.dto.auth.AuthLoginRequest;
import com.airin.taskmanager_backend.dto.auth.AuthRegisterRequest;
import com.airin.taskmanager_backend.dto.auth.AuthResponse;
import com.airin.taskmanager_backend.entity.User;
import com.airin.taskmanager_backend.repository.UserRepository;
import com.airin.taskmanager_backend.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody AuthRegisterRequest req) {
        if (userRepository.findByEmail(req.email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User saved = userRepository.save(new User(null, req.email, passwordEncoder.encode(req.password)));
        String token = jwtService.generateToken(saved.getEmail(), saved.getId());

        return new AuthResponse(token, saved.getId(), saved.getEmail());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthLoginRequest req) {
        User user = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(req.password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getId());
        return new AuthResponse(token, user.getId(), user.getEmail());
    }
}

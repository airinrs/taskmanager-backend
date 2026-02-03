package com.airin.taskmanager_backend.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthUtil {
    private final JwtService jwtService;

    public AuthUtil(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getUserId(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        Claims claims = jwtService.parseClaims(auth.substring(7));
        Object userId = claims.get("userId");
        return userId == null ? null : userId.toString();
    }
}

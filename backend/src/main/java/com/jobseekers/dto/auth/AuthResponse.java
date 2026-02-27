package com.jobseekers.dto.auth;

public record AuthResponse(String token, String tokenType, long expiresIn, String email, String role) {}

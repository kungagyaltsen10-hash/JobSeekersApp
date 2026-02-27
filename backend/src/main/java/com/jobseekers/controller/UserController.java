package com.jobseekers.controller;

import com.jobseekers.common.api.ApiResponse;
import com.jobseekers.dto.user.*;
import com.jobseekers.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> me() {
        return ResponseEntity.ok(ApiResponse.ok("Profile fetched", userService.me()));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> update(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Profile updated", userService.update(request)));
    }
}

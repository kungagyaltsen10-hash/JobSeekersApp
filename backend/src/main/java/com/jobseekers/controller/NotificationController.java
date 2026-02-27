package com.jobseekers.controller;

import com.jobseekers.common.api.ApiResponse;
import com.jobseekers.dto.notification.NotificationDto;
import com.jobseekers.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<NotificationDto>>> listMine() {
        return ResponseEntity.ok(ApiResponse.ok("Notifications fetched", notificationService.listMine()));
    }
}

package com.jobseekers.controller;

import com.jobseekers.common.api.ApiResponse;
import com.jobseekers.dto.application.ApplicationDto;
import com.jobseekers.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/{jobId}")
    public ResponseEntity<ApiResponse<ApplicationDto>> apply(@PathVariable Long jobId) {
        return ResponseEntity.ok(ApiResponse.ok("Application created", applicationService.apply(jobId)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> listMine() {
        return ResponseEntity.ok(ApiResponse.ok("Applications fetched", applicationService.listMine()));
    }
}

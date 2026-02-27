package com.jobseekers.controller;

import com.jobseekers.common.api.ApiResponse;
import com.jobseekers.dto.job.JobDto;
import com.jobseekers.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobDto>>> list() {
        return ResponseEntity.ok(ApiResponse.ok("Jobs fetched", jobService.listJobs()));
    }
}

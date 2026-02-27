package com.jobseekers.controller;

import com.jobseekers.common.api.ApiResponse;
import com.jobseekers.dto.resume.ResumeDto;
import com.jobseekers.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<ResumeDto>> upload(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.ok("Resume uploaded", resumeService.upload(file)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ResumeDto>>> listMine() {
        return ResponseEntity.ok(ApiResponse.ok("Resumes fetched", resumeService.listMine()));
    }
}

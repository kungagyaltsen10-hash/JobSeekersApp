package com.jobseekers.controller;

import com.jobseekers.common.api.ApiResponse;
import com.jobseekers.dto.location.*;
import com.jobseekers.service.LocationPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location-preferences")
@RequiredArgsConstructor
public class LocationPreferenceController {
    private final LocationPreferenceService service;

    @PostMapping
    public ResponseEntity<ApiResponse<LocationPreferenceDto>> upsert(@Valid @RequestBody LocationPreferenceRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Location preference saved", service.upsert(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<LocationPreferenceDto>> mine() {
        return ResponseEntity.ok(ApiResponse.ok("Location preference fetched", service.getMine()));
    }
}

package com.jobseekers.dto.application;

import com.jobseekers.domain.application.ApplicationStatus;
import java.time.Instant;

public record ApplicationDto(Long id, Long jobId, String jobTitle, ApplicationStatus status, Instant appliedDate, Double matchScore, Double locationScore) {}

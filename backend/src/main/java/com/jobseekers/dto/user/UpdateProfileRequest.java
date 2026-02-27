package com.jobseekers.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(@NotBlank String fullName, @Min(0) Integer yearsExperience, String preferredDomains, Double salaryExpectation) {}

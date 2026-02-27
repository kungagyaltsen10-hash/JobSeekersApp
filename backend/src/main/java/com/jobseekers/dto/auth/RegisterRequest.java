package com.jobseekers.dto.auth;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank String fullName,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        @NotNull @Min(0) Integer yearsExperience,
        String preferredDomains,
        @DecimalMin("0.0") Double salaryExpectation
) {}

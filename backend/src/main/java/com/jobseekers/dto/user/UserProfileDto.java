package com.jobseekers.dto.user;

public record UserProfileDto(Long id, String fullName, String email, Integer yearsExperience, String preferredDomains, Double salaryExpectation) {}

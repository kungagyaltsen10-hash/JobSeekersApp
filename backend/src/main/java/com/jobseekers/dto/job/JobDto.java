package com.jobseekers.dto.job;

import com.jobseekers.domain.job.WorkType;

public record JobDto(Long id, String title, String company, String description, String requiredSkills, Integer minExperience,
                     Double salaryMin, Double salaryMax, WorkType workType, String city, String state) {}

package com.jobseekers.dto.location;

import com.jobseekers.domain.job.WorkType;

public record LocationPreferenceDto(Long id, String preferredCities, WorkType workType, Boolean willingToRelocate, Integer maxCommuteRadiusKm, String preferredStates) {}

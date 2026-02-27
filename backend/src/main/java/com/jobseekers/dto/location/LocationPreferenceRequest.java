package com.jobseekers.dto.location;

import com.jobseekers.domain.job.WorkType;
import jakarta.validation.constraints.*;

public record LocationPreferenceRequest(
        @NotBlank String preferredCities,
        @NotNull WorkType workType,
        @NotNull Boolean willingToRelocate,
        @NotNull @Min(0) Integer maxCommuteRadiusKm,
        @NotBlank String preferredStates
) {}

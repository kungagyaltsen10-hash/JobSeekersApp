package com.jobseekers.dto.resume;

import java.time.Instant;

public record ResumeDto(Long id, String fileName, String contentType, Long fileSizeBytes, String storagePath, Boolean aiParsed, Instant uploadedAt) {}

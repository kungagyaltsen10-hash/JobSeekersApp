package com.jobseekers.dto.notification;

import java.time.Instant;

public record NotificationDto(Long id, String subject, String body, Boolean sent, Instant sentAt) {}

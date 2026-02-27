package com.jobseekers.service;

import com.jobseekers.dto.notification.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto notifyApplicationApplied(Long applicationId);
    List<NotificationDto> listMine();
}

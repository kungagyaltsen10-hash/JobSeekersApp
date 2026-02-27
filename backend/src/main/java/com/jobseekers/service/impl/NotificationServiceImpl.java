package com.jobseekers.service.impl;

import com.jobseekers.common.exception.ResourceNotFoundException;
import com.jobseekers.domain.notification.Notification;
import com.jobseekers.dto.notification.NotificationDto;
import com.jobseekers.infra.email.EmailService;
import com.jobseekers.mapper.DomainMapper;
import com.jobseekers.repository.ApplicationRepository;
import com.jobseekers.repository.NotificationRepository;
import com.jobseekers.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final ApplicationRepository applicationRepository;
    private final SecurityHelper securityHelper;
    private final EmailService emailService;
    private final DomainMapper mapper;

    @Override
    public NotificationDto notifyApplicationApplied(Long applicationId) {
        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        Notification notification = Notification.builder()
                .user(application.getUser())
                .subject("Application submitted: " + application.getJob().getTitle())
                .body("Your profile was auto-applied to " + application.getJob().getCompany() + " for role " + application.getJob().getTitle())
                .sent(true)
                .build();
        Notification saved = notificationRepository.save(notification);
        emailService.send(application.getUser().getEmail(), saved.getSubject(), saved.getBody());
        return mapper.toNotificationDto(saved);
    }

    @Override
    public List<NotificationDto> listMine() {
        var user = securityHelper.currentUser();
        return notificationRepository.findAll().stream().filter(n -> n.getUser().getId().equals(user.getId())).map(mapper::toNotificationDto).toList();
    }
}

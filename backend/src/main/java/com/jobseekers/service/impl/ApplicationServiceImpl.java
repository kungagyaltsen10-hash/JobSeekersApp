package com.jobseekers.service.impl;

import com.jobseekers.common.exception.BadRequestException;
import com.jobseekers.common.exception.ResourceNotFoundException;
import com.jobseekers.domain.application.Application;
import com.jobseekers.domain.application.ApplicationStatus;
import com.jobseekers.dto.application.ApplicationDto;
import com.jobseekers.mapper.DomainMapper;
import com.jobseekers.repository.ApplicationRepository;
import com.jobseekers.repository.JobRepository;
import com.jobseekers.service.ApplicationService;
import com.jobseekers.service.MatchingEngineService;
import com.jobseekers.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final SecurityHelper securityHelper;
    private final MatchingEngineService matchingEngineService;
    private final NotificationService notificationService;
    private final DomainMapper mapper;

    @Override
    public ApplicationDto apply(Long jobId) {
        var user = securityHelper.currentUser();
        var job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (applicationRepository.findByUserIdAndJobId(user.getId(), jobId).isPresent()) {
            throw new BadRequestException("Already applied to this job");
        }

        double matchScore = matchingEngineService.calculateTotalWeightedScore(user, job);
        double locationScore = matchingEngineService.calculateLocationScore(user, job);

        Application application = Application.builder()
                .user(user)
                .job(job)
                .status(ApplicationStatus.APPLIED)
                .matchScore(matchScore)
                .locationScore(locationScore)
                .build();

        Application saved = applicationRepository.save(application);
        notificationService.notifyApplicationApplied(saved.getId());
        return mapper.toApplicationDto(saved);
    }

    @Override
    public List<ApplicationDto> listMine() {
        return applicationRepository.findByUser(securityHelper.currentUser()).stream().map(mapper::toApplicationDto).toList();
    }
}

package com.jobseekers.domain.scheduler;

import com.jobseekers.domain.application.Application;
import com.jobseekers.domain.application.ApplicationStatus;
import com.jobseekers.repository.*;
import com.jobseekers.service.MatchingEngineService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoApplyQuartzJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AutoApplyQuartzJob.class);

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final MatchingEngineService matchingEngineService;

    @Value("${app.scheduler.match-threshold:70}")
    private double threshold;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("Auto apply scheduler started");
        var users = userRepository.findAll();
        var jobs = jobRepository.findAll();

        users.forEach(user -> jobs.forEach(job -> {
            if (applicationRepository.findByUserIdAndJobId(user.getId(), job.getId()).isPresent()) return;

            double score = matchingEngineService.calculateTotalWeightedScore(user, job);
            if (score >= threshold) {
                Application app = Application.builder()
                        .user(user)
                        .job(job)
                        .status(ApplicationStatus.APPLIED)
                        .matchScore(score)
                        .locationScore(matchingEngineService.calculateLocationScore(user, job))
                        .build();
                applicationRepository.save(app);
                log.info("Auto-applied user {} to job {} with score {}", user.getEmail(), job.getTitle(), score);
            }
        }));
    }
}

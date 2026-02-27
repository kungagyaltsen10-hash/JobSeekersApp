package com.jobseekers.config;

import com.jobseekers.domain.auth.*;
import com.jobseekers.domain.job.*;
import com.jobseekers.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final JobRepository jobRepository;
    private final JobLocationRepository jobLocationRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByName(RoleName.USER).isEmpty()) {
            roleRepository.save(Role.builder().name(RoleName.USER).build());
        }
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
            roleRepository.save(Role.builder().name(RoleName.ADMIN).build());
        }

        if (jobRepository.count() == 0) {
            Job job = jobRepository.save(Job.builder()
                    .title("Java Backend Engineer")
                    .company("TechNova India")
                    .description("Spring Boot and distributed systems")
                    .requiredSkills("java,spring,postgresql,redis")
                    .minExperience(3)
                    .salaryMin(BigDecimal.valueOf(1200000))
                    .salaryMax(BigDecimal.valueOf(2200000))
                    .workType(WorkType.HYBRID)
                    .build());

            jobLocationRepository.save(JobLocation.builder().job(job).city("Bengaluru").state("Karnataka").build());
        }
    }
}

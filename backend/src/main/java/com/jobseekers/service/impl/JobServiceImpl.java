package com.jobseekers.service.impl;

import com.jobseekers.dto.job.JobDto;
import com.jobseekers.mapper.DomainMapper;
import com.jobseekers.repository.JobLocationRepository;
import com.jobseekers.repository.JobRepository;
import com.jobseekers.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobLocationRepository jobLocationRepository;
    private final DomainMapper mapper;

    @Override
    @Cacheable("jobs")
    public List<JobDto> listJobs() {
        return jobRepository.findAll().stream().map(job -> {
            var location = jobLocationRepository.findByJobId(job.getId()).stream().findFirst().orElse(null);
            return mapper.toJobDto(job, location);
        }).toList();
    }
}

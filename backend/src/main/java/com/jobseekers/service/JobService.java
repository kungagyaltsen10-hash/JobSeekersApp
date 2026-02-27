package com.jobseekers.service;

import com.jobseekers.dto.job.JobDto;

import java.util.List;

public interface JobService {
    List<JobDto> listJobs();
}

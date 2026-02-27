package com.jobseekers.repository;

import com.jobseekers.domain.job.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobLocationRepository extends JpaRepository<JobLocation, Long> {
    List<JobLocation> findByJobId(Long jobId);
}

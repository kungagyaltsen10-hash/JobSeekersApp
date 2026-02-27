package com.jobseekers.repository;

import com.jobseekers.domain.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}

package com.jobseekers.repository;

import com.jobseekers.domain.application.Application;
import com.jobseekers.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUserIdAndJobId(Long userId, Long jobId);
    List<Application> findByUser(User user);
}

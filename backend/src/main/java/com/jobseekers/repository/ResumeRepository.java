package com.jobseekers.repository;

import com.jobseekers.domain.resume.Resume;
import com.jobseekers.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUserOrderByUploadedAtDesc(User user);
}

package com.jobseekers.service;

import com.jobseekers.domain.job.Job;
import com.jobseekers.domain.user.User;

public interface MatchingEngineService {
    double calculateSkillScore(User user, Job job);
    double calculateExperienceScore(User user, Job job);
    double calculateLocationScore(User user, Job job);
    double calculateTotalWeightedScore(User user, Job job);
}

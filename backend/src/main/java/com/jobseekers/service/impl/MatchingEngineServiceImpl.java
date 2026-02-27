package com.jobseekers.service.impl;

import com.jobseekers.domain.job.Job;
import com.jobseekers.domain.user.User;
import com.jobseekers.repository.UserLocationPreferenceRepository;
import com.jobseekers.service.MatchingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingEngineServiceImpl implements MatchingEngineService {
    private final UserLocationPreferenceRepository locationPreferenceRepository;

    @Override
    public double calculateSkillScore(User user, Job job) {
        Set<String> userDomains = splitToSet(user.getPreferredDomains());
        Set<String> jobSkills = splitToSet(job.getRequiredSkills());
        if (jobSkills.isEmpty()) return 50;
        long matches = jobSkills.stream().filter(userDomains::contains).count();
        return (matches * 100.0) / jobSkills.size();
    }

    @Override
    public double calculateExperienceScore(User user, Job job) {
        if (job.getMinExperience() == null || job.getMinExperience() <= 0) return 100;
        return Math.min(100.0, (user.getYearsExperience() * 100.0) / job.getMinExperience());
    }

    @Override
    public double calculateLocationScore(User user, Job job) {
        return locationPreferenceRepository.findByUser(user)
                .map(pref -> pref.getPreferredStates().contains("Remote") || pref.getPreferredStates().contains("ANY") ? 100.0 : 75.0)
                .orElse(50.0);
    }

    @Override
    public double calculateTotalWeightedScore(User user, Job job) {
        double skill = calculateSkillScore(user, job);
        double exp = calculateExperienceScore(user, job);
        double loc = calculateLocationScore(user, job);
        return (skill * 0.45) + (exp * 0.30) + (loc * 0.25);
    }

    private Set<String> splitToSet(String value) {
        if (value == null || value.isBlank()) return Set.of();
        return Arrays.stream(value.toLowerCase().split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());
    }
}

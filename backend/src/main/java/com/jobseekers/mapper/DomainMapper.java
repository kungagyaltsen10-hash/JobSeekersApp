package com.jobseekers.mapper;

import com.jobseekers.domain.application.Application;
import com.jobseekers.domain.job.Job;
import com.jobseekers.domain.job.JobLocation;
import com.jobseekers.domain.location.UserLocationPreference;
import com.jobseekers.domain.notification.Notification;
import com.jobseekers.domain.resume.Resume;
import com.jobseekers.domain.user.User;
import com.jobseekers.dto.application.ApplicationDto;
import com.jobseekers.dto.job.JobDto;
import com.jobseekers.dto.location.LocationPreferenceDto;
import com.jobseekers.dto.notification.NotificationDto;
import com.jobseekers.dto.resume.ResumeDto;
import com.jobseekers.dto.user.UserProfileDto;
import org.springframework.stereotype.Component;

@Component
public class DomainMapper {

    public UserProfileDto toUserProfile(User user) {
        return new UserProfileDto(user.getId(), user.getFullName(), user.getEmail(), user.getYearsExperience(),
                user.getPreferredDomains(), user.getSalaryExpectation() == null ? null : user.getSalaryExpectation().doubleValue());
    }

    public LocationPreferenceDto toLocationPreference(UserLocationPreference p) {
        return new LocationPreferenceDto(p.getId(), p.getPreferredCities(), p.getWorkType(), p.getWillingToRelocate(),
                p.getMaxCommuteRadiusKm(), p.getPreferredStates());
    }

    public ResumeDto toResumeDto(Resume r) {
        return new ResumeDto(r.getId(), r.getFileName(), r.getContentType(), r.getFileSizeBytes(), r.getStoragePath(), r.getAiParsed(), r.getUploadedAt());
    }

    public JobDto toJobDto(Job j, JobLocation location) {
        return new JobDto(j.getId(), j.getTitle(), j.getCompany(), j.getDescription(), j.getRequiredSkills(), j.getMinExperience(),
                j.getSalaryMin() == null ? null : j.getSalaryMin().doubleValue(),
                j.getSalaryMax() == null ? null : j.getSalaryMax().doubleValue(),
                j.getWorkType(), location == null ? null : location.getCity(), location == null ? null : location.getState());
    }

    public ApplicationDto toApplicationDto(Application a) {
        return new ApplicationDto(a.getId(), a.getJob().getId(), a.getJob().getTitle(), a.getStatus(), a.getAppliedDate(), a.getMatchScore(), a.getLocationScore());
    }

    public NotificationDto toNotificationDto(Notification n) {
        return new NotificationDto(n.getId(), n.getSubject(), n.getBody(), n.getSent(), n.getSentAt());
    }
}

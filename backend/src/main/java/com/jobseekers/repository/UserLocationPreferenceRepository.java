package com.jobseekers.repository;

import com.jobseekers.domain.location.UserLocationPreference;
import com.jobseekers.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLocationPreferenceRepository extends JpaRepository<UserLocationPreference, Long> {
    Optional<UserLocationPreference> findByUser(User user);
}

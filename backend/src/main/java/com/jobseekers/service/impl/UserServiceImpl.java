package com.jobseekers.service.impl;

import com.jobseekers.dto.user.*;
import com.jobseekers.mapper.DomainMapper;
import com.jobseekers.repository.UserRepository;
import com.jobseekers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final SecurityHelper securityHelper;
    private final UserRepository userRepository;
    private final DomainMapper mapper;

    @Override
    public UserProfileDto me() { return mapper.toUserProfile(securityHelper.currentUser()); }

    @Override
    public UserProfileDto update(UpdateProfileRequest request) {
        var user = securityHelper.currentUser();
        user.setFullName(request.fullName());
        user.setYearsExperience(request.yearsExperience());
        user.setPreferredDomains(request.preferredDomains());
        user.setSalaryExpectation(request.salaryExpectation() == null ? null : BigDecimal.valueOf(request.salaryExpectation()));
        return mapper.toUserProfile(userRepository.save(user));
    }
}

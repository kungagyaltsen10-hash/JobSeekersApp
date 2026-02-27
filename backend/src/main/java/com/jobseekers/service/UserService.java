package com.jobseekers.service;

import com.jobseekers.dto.user.*;

public interface UserService {
    UserProfileDto me();
    UserProfileDto update(UpdateProfileRequest request);
}

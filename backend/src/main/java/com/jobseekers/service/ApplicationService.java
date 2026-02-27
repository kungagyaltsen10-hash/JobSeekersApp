package com.jobseekers.service;

import com.jobseekers.dto.application.ApplicationDto;

import java.util.List;

public interface ApplicationService {
    ApplicationDto apply(Long jobId);
    List<ApplicationDto> listMine();
}

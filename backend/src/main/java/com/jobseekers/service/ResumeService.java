package com.jobseekers.service;

import com.jobseekers.dto.resume.ResumeDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    ResumeDto upload(MultipartFile file);
    List<ResumeDto> listMine();
}

package com.jobseekers.service.impl;

import com.jobseekers.common.exception.BadRequestException;
import com.jobseekers.domain.resume.Resume;
import com.jobseekers.dto.resume.ResumeDto;
import com.jobseekers.infra.storage.FileStorageService;
import com.jobseekers.mapper.DomainMapper;
import com.jobseekers.repository.ResumeRepository;
import com.jobseekers.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final SecurityHelper securityHelper;
    private final ResumeRepository resumeRepository;
    private final DomainMapper mapper;
    private final FileStorageService fileStorageService;

    @Override
    public ResumeDto upload(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new BadRequestException("Resume file is required");
        String ct = file.getContentType() == null ? "" : file.getContentType();
        if (!(ct.contains("pdf") || ct.contains("msword") || ct.contains("officedocument"))) {
            throw new BadRequestException("Only PDF/DOC/DOCX files are allowed");
        }

        String path = fileStorageService.storeResume(file);
        Resume resume = Resume.builder()
                .user(securityHelper.currentUser())
                .fileName(file.getOriginalFilename())
                .contentType(ct)
                .fileSizeBytes(file.getSize())
                .storagePath(path)
                .aiParsed(false)
                .build();
        return mapper.toResumeDto(resumeRepository.save(resume));
    }

    @Override
    public List<ResumeDto> listMine() {
        return resumeRepository.findByUserOrderByUploadedAtDesc(securityHelper.currentUser()).stream().map(mapper::toResumeDto).toList();
    }
}

package com.jobseekers.infra.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.storage.resume-dir}")
    private String resumeDir;

    public String storeResume(MultipartFile file) {
        try {
            Path dir = Paths.get(resumeDir);
            Files.createDirectories(dir);
            String name = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = dir.resolve(name);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store resume", e);
        }
    }
}

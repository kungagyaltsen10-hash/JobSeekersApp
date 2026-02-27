package com.jobseekers.domain.resume;

import com.jobseekers.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "resumes", indexes = {
        @Index(name = "idx_resume_user", columnList = "user_id"),
        @Index(name = "idx_resume_uploaded", columnList = "uploadedAt")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resume {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String fileName;

    @Column(nullable = false, length = 30)
    private String contentType;

    @Column(nullable = false)
    private Long fileSizeBytes;

    @Column(nullable = false, length = 255)
    private String storagePath;

    @Column(nullable = false)
    private Boolean aiParsed = false;

    @Column(nullable = false)
    private Instant uploadedAt;

    @PrePersist
    public void prePersist() {
        uploadedAt = Instant.now();
    }
}

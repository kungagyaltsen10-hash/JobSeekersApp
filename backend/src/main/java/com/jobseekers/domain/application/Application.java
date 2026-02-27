package com.jobseekers.domain.application;

import com.jobseekers.domain.job.Job;
import com.jobseekers.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "applications", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"}), indexes = {
        @Index(name = "idx_application_user_status", columnList = "user_id,status"),
        @Index(name = "idx_application_applied_date", columnList = "appliedDate")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Application {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApplicationStatus status;

    @Column(nullable = false)
    private Instant appliedDate;

    @Column(nullable = false)
    private Double matchScore;

    @Column(nullable = false)
    private Double locationScore;

    @PrePersist
    public void prePersist() {
        appliedDate = Instant.now();
    }
}

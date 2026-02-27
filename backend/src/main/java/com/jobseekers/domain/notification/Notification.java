package com.jobseekers.domain.notification;

import com.jobseekers.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_notification_user_sent", columnList = "user_id,sentAt")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 150)
    private String subject;

    @Column(nullable = false, length = 1000)
    private String body;

    @Column(nullable = false)
    private Boolean sent;

    @Column(nullable = false)
    private Instant sentAt;

    @PrePersist
    public void prePersist() {
        sentAt = Instant.now();
    }
}

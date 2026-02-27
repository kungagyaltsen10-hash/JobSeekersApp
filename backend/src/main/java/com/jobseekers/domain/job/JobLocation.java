package com.jobseekers.domain.job;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_locations", indexes = {
        @Index(name = "idx_job_location_job", columnList = "job_id"),
        @Index(name = "idx_job_location_state_city", columnList = "state,city")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobLocation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;
}

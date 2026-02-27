package com.jobseekers.domain.job;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "jobs", indexes = {
        @Index(name = "idx_job_company", columnList = "company"),
        @Index(name = "idx_job_work_type", columnList = "workType"),
        @Index(name = "idx_job_salary", columnList = "salaryMin,salaryMax")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 150)
    private String company;

    @Column(length = 500)
    private String description;

    @Column(length = 255)
    private String requiredSkills;

    private Integer minExperience;

    @Column(precision = 12, scale = 2)
    private BigDecimal salaryMin;

    @Column(precision = 12, scale = 2)
    private BigDecimal salaryMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WorkType workType;
}

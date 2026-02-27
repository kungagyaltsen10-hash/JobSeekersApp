package com.jobseekers.domain.location;

import com.jobseekers.domain.job.WorkType;
import com.jobseekers.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_location_preferences", indexes = {
        @Index(name = "idx_location_user", columnList = "user_id"),
        @Index(name = "idx_location_work_type", columnList = "workType"),
        @Index(name = "idx_location_state_city", columnList = "preferredStates,preferredCities")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserLocationPreference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 255)
    private String preferredCities;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WorkType workType;

    @Column(nullable = false)
    private Boolean willingToRelocate;

    @Column(nullable = false)
    private Integer maxCommuteRadiusKm;

    @Column(nullable = false, length = 255)
    private String preferredStates;
}

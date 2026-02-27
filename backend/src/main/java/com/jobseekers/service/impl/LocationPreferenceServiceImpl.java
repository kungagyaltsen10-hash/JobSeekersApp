package com.jobseekers.service.impl;

import com.jobseekers.dto.location.*;
import com.jobseekers.mapper.DomainMapper;
import com.jobseekers.repository.UserLocationPreferenceRepository;
import com.jobseekers.service.LocationPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationPreferenceServiceImpl implements LocationPreferenceService {
    private final SecurityHelper securityHelper;
    private final UserLocationPreferenceRepository repository;
    private final DomainMapper mapper;

    @Override
    public LocationPreferenceDto upsert(LocationPreferenceRequest request) {
        var user = securityHelper.currentUser();
        var pref = repository.findByUser(user).orElseGet(() -> com.jobseekers.domain.location.UserLocationPreference.builder().user(user).build());
        pref.setPreferredCities(request.preferredCities());
        pref.setPreferredStates(request.preferredStates());
        pref.setWorkType(request.workType());
        pref.setWillingToRelocate(request.willingToRelocate());
        pref.setMaxCommuteRadiusKm(request.maxCommuteRadiusKm());
        return mapper.toLocationPreference(repository.save(pref));
    }

    @Override
    public LocationPreferenceDto getMine() {
        var user = securityHelper.currentUser();
        return repository.findByUser(user).map(mapper::toLocationPreference).orElse(null);
    }
}

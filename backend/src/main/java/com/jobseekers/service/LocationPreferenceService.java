package com.jobseekers.service;

import com.jobseekers.dto.location.*;

public interface LocationPreferenceService {
    LocationPreferenceDto upsert(LocationPreferenceRequest request);
    LocationPreferenceDto getMine();
}

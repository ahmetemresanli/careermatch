package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.dto.response.SimilarJobResponse;
import java.util.List;

public interface IJobSimilarityService { List<SimilarJobResponse> findSimilar(Long jobId, int limit); }

package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.response.SimilarJobResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface IJobSimilarityController { ResponseEntity<List<SimilarJobResponse>> findSimilar(Long jobId, int limit); }

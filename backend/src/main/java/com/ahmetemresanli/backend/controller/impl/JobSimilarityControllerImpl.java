package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IJobSimilarityController;
import com.ahmetemresanli.backend.dto.response.SimilarJobResponse;
import com.ahmetemresanli.backend.service.IJobSimilarityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/job-postings")
public class JobSimilarityControllerImpl implements IJobSimilarityController {
    private final IJobSimilarityService service; public JobSimilarityControllerImpl(IJobSimilarityService service) { this.service = service; }
    @Override @GetMapping("/{jobId}/similar") public ResponseEntity<List<SimilarJobResponse>> findSimilar(
            @PathVariable Long jobId, @RequestParam(defaultValue = "10") int limit) { return ResponseEntity.ok(service.findSimilar(jobId, limit)); }
}

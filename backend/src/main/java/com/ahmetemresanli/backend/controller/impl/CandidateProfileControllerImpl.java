package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICandidateProfileController;
import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.service.ICandidateProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-profiles")
public class CandidateProfileControllerImpl implements ICandidateProfileController {

    private final ICandidateProfileService candidateProfileService;

    public CandidateProfileControllerImpl(ICandidateProfileService candidateProfileService){
        this.candidateProfileService = candidateProfileService;
    }


    @Override
    @PostMapping("/user/{userId}")
    public ResponseEntity<CandidateProfile> createCandidateProfile(
            @PathVariable Long userId,
            @RequestBody CandidateProfile candidateProfile
    ) {
        CandidateProfile savedProfile =
                candidateProfileService.createCandidateProfile(userId, candidateProfile);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CandidateProfile> getCandidateProfileById(@PathVariable Long id) {
        CandidateProfile candidateProfile = candidateProfileService.getCandidateProfileById(id);

        return ResponseEntity.ok(candidateProfile);
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<CandidateProfile> getCandidateProfileByUserId(@PathVariable Long userId) {
        CandidateProfile candidateProfile = candidateProfileService.getCandidateProfileByUserId(userId);

        return ResponseEntity.ok(candidateProfile);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CandidateProfile>> getAllCandidateProfiles() {
        List<CandidateProfile> candidateProfiles = candidateProfileService.getAllCandidateProfiles();

        return ResponseEntity.ok(candidateProfiles);
    }
}

package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IMatchController;
import com.ahmetemresanli.backend.dto.response.MatchResponse;
import com.ahmetemresanli.backend.entity.Match;
import com.ahmetemresanli.backend.mapper.MatchMapper;
import com.ahmetemresanli.backend.service.IMatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchControllerImpl implements IMatchController {

    private final IMatchService matchService;

    public MatchControllerImpl(
            IMatchService matchService
    ) {
        this.matchService = matchService;
    }

    @Override
    @PostMapping(
            "/candidate/{candidateProfileId}/job/{jobPostingId}"
    )
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId) or @access.managesJob(#jobPostingId)")
    public ResponseEntity<MatchResponse> calculateMatch(
            @PathVariable Long candidateProfileId,
            @PathVariable Long jobPostingId
    ) {

        Match match =
                matchService.calculateAndSaveMatch(
                        candidateProfileId,
                        jobPostingId
                );

        return ResponseEntity.ok(
                MatchMapper.toResponse(match)
        );
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("@access.canAccessMatch(#id)")
    public ResponseEntity<MatchResponse> getMatchById(
            @PathVariable Long id
    ) {

        Match match =
                matchService.getMatchById(id);

        return ResponseEntity.ok(
                MatchMapper.toResponse(match)
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<List<MatchResponse>> getMatchesByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<MatchResponse> responses =
                matchService
                        .getMatchesByCandidateProfileId(
                                candidateProfileId
                        )
                        .stream()
                        .map(MatchMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/job/{jobPostingId}")
    @PreAuthorize("@access.managesJob(#jobPostingId)")
    public ResponseEntity<List<MatchResponse>> getMatchesByJobPosting(
            @PathVariable Long jobPostingId
    ) {

        List<MatchResponse> responses =
                matchService
                        .getMatchesByJobPostingId(
                                jobPostingId
                        )
                        .stream()
                        .map(MatchMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @PostMapping(
            "/candidate/{candidateProfileId}/calculate-all"
    )
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<List<MatchResponse>>
    calculateMatchesForCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<MatchResponse> responses =
                matchService
                        .calculateMatchesForCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(MatchMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @PostMapping("/job/{jobPostingId}/calculate-all")
    @PreAuthorize("@access.managesJob(#jobPostingId)")
    public ResponseEntity<List<MatchResponse>>
    calculateMatchesForJobPosting(@PathVariable Long jobPostingId) {

        List<MatchResponse> responses =
                matchService
                        .calculateMatchesForJobPosting(
                                jobPostingId
                        )
                        .stream()
                        .map(MatchMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}/recommendations")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<List<MatchResponse>> getCandidateRecommendations(
            @PathVariable Long candidateProfileId
    ) {

        List<MatchResponse> responses =
                matchService
                        .getCandidateRecommendations(candidateProfileId)
                        .stream()
                        .map(MatchMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/job/{jobPostingId}/recommendations")
    @PreAuthorize("@access.managesJob(#jobPostingId)")
    public ResponseEntity<List<MatchResponse>> getJobPostingRecommendations(
            @PathVariable Long jobPostingId
    ) {

        List<MatchResponse> responses =
                matchService
                        .getJobPostingRecommendations(jobPostingId)
                        .stream()
                        .map(MatchMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}

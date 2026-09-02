package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IMatchController;
import com.ahmetemresanli.backend.dto.response.MatchResponse;
import com.ahmetemresanli.backend.entity.Match;
import com.ahmetemresanli.backend.mapper.MatchMapper;
import com.ahmetemresanli.backend.service.IMatchService;
import org.springframework.http.ResponseEntity;
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
}
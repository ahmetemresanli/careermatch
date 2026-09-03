package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.response.MatchResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMatchController {

    ResponseEntity<MatchResponse> calculateMatch(
            Long candidateProfileId,
            Long jobPostingId
    );

    ResponseEntity<MatchResponse> getMatchById(
            Long id
    );

    ResponseEntity<List<MatchResponse>> getMatchesByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<List<MatchResponse>> getMatchesByJobPosting(
            Long jobPostingId
    );

    ResponseEntity<List<MatchResponse>>
    calculateMatchesForCandidate(
            Long candidateProfileId
    );

    ResponseEntity<List<MatchResponse>>
    calculateMatchesForJobPosting(
            Long jobPostingId
    );

    ResponseEntity<List<MatchResponse>> getCandidateRecommendations(
            Long candidateProfileId
    );

    ResponseEntity<List<MatchResponse>> getJobPostingRecommendations(
            Long jobPostingId
    );
}
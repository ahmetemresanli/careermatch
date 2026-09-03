package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Match;

import java.util.List;

public interface IMatchService {

    Match calculateAndSaveMatch(
            Long candidateProfileId,
            Long jobPostingId
    );

    List<Match> calculateMatchesForCandidate(
            Long candidateProfileId
    );

    Match getMatchById(Long id);

    List<Match> getMatchesByCandidateProfileId(
            Long candidateProfileId
    );

    List<Match> getMatchesByJobPostingId(
            Long jobPostingId
    );

    List<Match> calculateMatchesForJobPosting(
            Long jobPostingId
    );

    List<Match> getCandidateRecommendations(
            Long candidateProfileId
    );

    List<Match> getJobPostingRecommendations(
            Long jobPostingId
    );
}
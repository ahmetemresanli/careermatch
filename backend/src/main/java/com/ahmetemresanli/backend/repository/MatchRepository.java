package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Match;
import com.ahmetemresanli.backend.enums.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByCandidateProfileIdAndJobPostingId(
            Long candidateProfileId,
            Long jobPostingId
    );

    List<Match> findByCandidateProfileId(
            Long candidateProfileId
    );

    List<Match> findByJobPostingId(
            Long jobPostingId
    );

    boolean existsByCandidateProfileIdAndJobPostingId(
            Long candidateProfileId,
            Long jobPostingId
    );

    List<Match> findByCandidateProfileIdAndStatusAndScoreGreaterThanEqualOrderByScoreDesc(
            Long candidateProfileId,
            MatchStatus status,
            BigDecimal minimumScore
    );

    List<Match> findByJobPostingIdAndStatusAndScoreGreaterThanEqualOrderByScoreDesc(
            Long jobPostingId,
            MatchStatus status,
            BigDecimal minimumScore
    );
}
package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long> {

    Optional<Conversation>
    findByCandidateProfileIdAndCompanyId(
            Long candidateProfileId,
            Long companyId
    );

    boolean existsByCandidateProfileIdAndCompanyId(
            Long candidateProfileId,
            Long companyId
    );

    List<Conversation>
    findByCandidateProfileIdOrderByUpdatedAtDesc(
            Long candidateProfileId
    );

    List<Conversation>
    findByCompanyIdOrderByUpdatedAtDesc(
            Long companyId
    );
}
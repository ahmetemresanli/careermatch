package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.entity.SkillEndorsement;
import com.ahmetemresanli.backend.enums.ReferenceRelation;
import com.ahmetemresanli.backend.enums.SkillEndorsementStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.CandidateSkillRepository;
import com.ahmetemresanli.backend.repository.SkillEndorsementRepository;
import com.ahmetemresanli.backend.service.ISkillEndorsementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SkillEndorsementServiceImpl
        implements ISkillEndorsementService {

    private static final long ENDORSEMENT_TOKEN_VALID_DAYS = 7;

    private final SkillEndorsementRepository skillEndorsementRepository;
    private final CandidateSkillRepository candidateSkillRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public SkillEndorsementServiceImpl(
            SkillEndorsementRepository skillEndorsementRepository,
            CandidateSkillRepository candidateSkillRepository,
            CandidateProfileRepository candidateProfileRepository
    ) {
        this.skillEndorsementRepository = skillEndorsementRepository;
        this.candidateSkillRepository = candidateSkillRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    @Override
    @Transactional
    public SkillEndorsement createEndorsementRequest(
            Long candidateSkillId,
            String endorserName,
            String endorserEmail,
            ReferenceRelation relation,
            String requestMessage
    ) {

        CandidateSkill candidateSkill =
                candidateSkillRepository
                        .findById(candidateSkillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate skill not found"
                                )
                        );

        if (endorserName == null || endorserName.isBlank()) {
            throw new BusinessException(
                    "Endorser name cannot be empty"
            );
        }

        if (endorserEmail == null || endorserEmail.isBlank()) {
            throw new BusinessException(
                    "Endorser email cannot be empty"
            );
        }

        if (relation == null) {
            throw new BusinessException(
                    "Endorser relation cannot be null"
            );
        }

        String normalizedEmail =
                endorserEmail.trim().toLowerCase();

        CandidateProfile candidateProfile =
                candidateSkill.getCandidateProfile();

        /*
         * Aday kendi skill'ini kendi hesabının
         * email adresiyle onaylayamaz.
         */
        if (candidateProfile != null
                && candidateProfile.getUser() != null
                && candidateProfile.getUser().getEmail() != null
                && candidateProfile.getUser()
                .getEmail()
                .equalsIgnoreCase(normalizedEmail)) {

            throw new BusinessException(
                    "Candidate cannot endorse their own skill"
            );
        }

        /*
         * Aynı kişi aynı CandidateSkill için
         * ikinci kez endorsement oluşturamaz.
         */
        if (skillEndorsementRepository
                .findByCandidateSkillIdAndEndorserEmailIgnoreCase(
                        candidateSkillId,
                        normalizedEmail
                )
                .isPresent()) {

            throw new BusinessException(
                    "This person has already received an endorsement request for this skill"
            );
        }

        SkillEndorsement endorsement =
                new SkillEndorsement();

        endorsement.setCandidateSkill(
                candidateSkill
        );

        endorsement.setEndorserName(
                endorserName.trim()
        );

        endorsement.setEndorserEmail(
                normalizedEmail
        );

        endorsement.setRelation(
                relation
        );

        endorsement.setRequestMessage(
                requestMessage == null
                        ? null
                        : requestMessage.trim()
        );

        endorsement.setStatus(
                SkillEndorsementStatus.PENDING
        );

        endorsement.setToken(
                UUID.randomUUID().toString()
        );

        endorsement.setExpiresAt(
                LocalDateTime.now()
                        .plusDays(
                                ENDORSEMENT_TOKEN_VALID_DAYS
                        )
        );

        return skillEndorsementRepository.save(
                endorsement
        );
    }

    @Override
    @Transactional
    public SkillEndorsement endorseSkill(
            String token,
            String endorsementComment
    ) {

        SkillEndorsement endorsement =
                getPendingEndorsementByToken(token);

        endorsement.setStatus(
                SkillEndorsementStatus.ENDORSED
        );

        endorsement.setEndorsementComment(
                endorsementComment == null
                        ? null
                        : endorsementComment.trim()
        );

        endorsement.setRespondedAt(
                LocalDateTime.now()
        );

        return skillEndorsementRepository.save(
                endorsement
        );
    }

    @Override
    @Transactional
    public SkillEndorsement rejectEndorsement(
            String token
    ) {

        SkillEndorsement endorsement =
                getPendingEndorsementByToken(token);

        endorsement.setStatus(
                SkillEndorsementStatus.REJECTED
        );

        endorsement.setRespondedAt(
                LocalDateTime.now()
        );

        return skillEndorsementRepository.save(
                endorsement
        );
    }

    @Override
    public SkillEndorsement getEndorsementById(
            Long id
    ) {

        return skillEndorsementRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill endorsement not found"
                        )
                );
    }

    @Override
    public List<SkillEndorsement>
    getEndorsementsByCandidateSkill(
            Long candidateSkillId
    ) {

        if (!candidateSkillRepository
                .existsById(candidateSkillId)) {

            throw new ResourceNotFoundException(
                    "Candidate skill not found"
            );
        }

        return skillEndorsementRepository
                .findByCandidateSkillIdOrderByCreatedAtDesc(
                        candidateSkillId
                );
    }

    @Override
    public List<SkillEndorsement>
    getEndorsementsByCandidate(
            Long candidateProfileId
    ) {

        validateCandidate(candidateProfileId);

        return skillEndorsementRepository
                .findByCandidateSkillCandidateProfileIdOrderByCreatedAtDesc(
                        candidateProfileId
                );
    }

    @Override
    public List<SkillEndorsement>
    getApprovedEndorsementsByCandidate(
            Long candidateProfileId
    ) {

        validateCandidate(candidateProfileId);

        return skillEndorsementRepository
                .findByCandidateSkillCandidateProfileIdAndStatusOrderByCreatedAtDesc(
                        candidateProfileId,
                        SkillEndorsementStatus.ENDORSED
                );
    }

    private SkillEndorsement getPendingEndorsementByToken(
            String token
    ) {

        if (token == null || token.isBlank()) {
            throw new BusinessException(
                    "Endorsement token cannot be empty"
            );
        }

        SkillEndorsement endorsement =
                skillEndorsementRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill endorsement token not found"
                                )
                        );

        if (endorsement.getStatus()
                != SkillEndorsementStatus.PENDING) {

            throw new BusinessException(
                    "Skill endorsement request is no longer pending"
            );
        }

        if (endorsement.getExpiresAt() == null
                || endorsement.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            endorsement.setStatus(
                    SkillEndorsementStatus.EXPIRED
            );

            skillEndorsementRepository.save(
                    endorsement
            );

            throw new BusinessException(
                    "Skill endorsement request has expired"
            );
        }

        return endorsement;
    }

    private void validateCandidate(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }
    }
}
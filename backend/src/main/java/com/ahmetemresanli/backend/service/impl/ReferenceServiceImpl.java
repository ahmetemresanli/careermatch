package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Reference;
import com.ahmetemresanli.backend.entity.ReferenceRequest;
import com.ahmetemresanli.backend.enums.ReferenceRelation;
import com.ahmetemresanli.backend.enums.ReferenceRequestStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.ReferenceRepository;
import com.ahmetemresanli.backend.repository.ReferenceRequestRepository;
import com.ahmetemresanli.backend.service.IReferenceService;
import com.ahmetemresanli.backend.service.IMailService;
import com.ahmetemresanli.backend.service.INotificationService;
import com.ahmetemresanli.backend.enums.NotificationType;
import com.ahmetemresanli.backend.security.SecureTokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReferenceServiceImpl
        implements IReferenceService {

    private static final long REFERENCE_TOKEN_VALID_DAYS = 7;

    private final ReferenceRequestRepository referenceRequestRepository;
    private final ReferenceRepository referenceRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final IMailService mailService;
    private final INotificationService notificationService;
    private final String publicBaseUrl;

    public ReferenceServiceImpl(
            ReferenceRequestRepository referenceRequestRepository,
            ReferenceRepository referenceRepository,
            CandidateProfileRepository candidateProfileRepository,
            IMailService mailService,
            INotificationService notificationService,
            @Value("${app.public-base-url:http://localhost:8080}") String publicBaseUrl
    ) {
        this.referenceRequestRepository = referenceRequestRepository;
        this.referenceRepository = referenceRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.mailService = mailService;
        this.notificationService = notificationService;
        this.publicBaseUrl = publicBaseUrl;
    }

    @Override
    @Transactional
    public ReferenceRequest createReferenceRequest(
            Long candidateProfileId,
            String referenceName,
            String referenceEmail,
            ReferenceRelation relation,
            String requestMessage
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository
                        .findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        if (referenceName == null
                || referenceName.isBlank()) {

            throw new BusinessException(
                    "Reference name cannot be empty"
            );
        }

        if (referenceEmail == null
                || referenceEmail.isBlank()) {

            throw new BusinessException(
                    "Reference email cannot be empty"
            );
        }

        if (relation == null) {

            throw new BusinessException(
                    "Reference relation cannot be null"
            );
        }

        String normalizedEmail =
                referenceEmail
                        .trim()
                        .toLowerCase();

        /*
         * Kullanıcı kendi kendisine referans veremez.
         */
        if (candidateProfile.getUser() != null
                && candidateProfile.getUser().getEmail() != null
                && candidateProfile.getUser()
                .getEmail()
                .equalsIgnoreCase(normalizedEmail)) {

            throw new BusinessException(
                    "Candidate cannot request a reference from their own email"
            );
        }

        /*
         * Aynı kişiye zaten PENDING istek varsa
         * tekrar oluşturma.
         */
        referenceRequestRepository
                .findFirstByCandidateProfileIdAndReferenceEmailIgnoreCaseAndStatusOrderByCreatedAtDesc(
                        candidateProfileId,
                        normalizedEmail,
                        ReferenceRequestStatus.PENDING
                )
                .ifPresent(existingRequest -> {

                    /*
                     * Süresi geçmişse EXPIRED yapıp
                     * yeni isteğe izin ver.
                     */
                    if (existingRequest.getExpiresAt() != null
                            && existingRequest.getExpiresAt()
                            .isBefore(LocalDateTime.now())) {

                        existingRequest.setStatus(
                                ReferenceRequestStatus.EXPIRED
                        );

                        referenceRequestRepository.save(
                                existingRequest
                        );

                        return;
                    }

                    throw new com.ahmetemresanli.backend.exception.DuplicateResourceException(
                            "A pending reference request already exists for this email"
                    );
                });

        ReferenceRequest referenceRequest =
                new ReferenceRequest();

        referenceRequest.setCandidateProfile(
                candidateProfile
        );

        referenceRequest.setReferenceName(
                referenceName.trim()
        );

        referenceRequest.setReferenceEmail(
                normalizedEmail
        );

        referenceRequest.setRelation(
                relation
        );

        referenceRequest.setRequestMessage(
                requestMessage == null
                        ? null
                        : requestMessage.trim()
        );

        referenceRequest.setStatus(
                ReferenceRequestStatus.PENDING
        );

        referenceRequest.setToken(SecureTokenGenerator.generate());

        referenceRequest.setExpiresAt(
                LocalDateTime.now()
                        .plusDays(
                                REFERENCE_TOKEN_VALID_DAYS
                        )
        );

        ReferenceRequest saved = referenceRequestRepository.save(referenceRequest);
        mailService.send(saved.getReferenceEmail(), "CareerMatch reference request",
                "Respond to the reference request: " + publicBaseUrl + "/api/references/accept?token=" + saved.getToken());
        return saved;
    }

    @Override
    @Transactional
    public Reference acceptReferenceRequest(
            String token,
            String referenceText,
            String organizationName,
            String positionTitle
    ) {

        ReferenceRequest request =
                getPendingRequestByToken(token);

        if (referenceText == null
                || referenceText.isBlank()) {

            throw new BusinessException(
                    "Reference text cannot be empty"
            );
        }

        if (referenceRepository
                .existsByReferenceRequestId(
                        request.getId()
                )) {

            throw new com.ahmetemresanli.backend.exception.DuplicateResourceException(
                    "Reference has already been created for this request"
            );
        }

        Reference reference =
                new Reference();

        reference.setReferenceRequest(
                request
        );

        reference.setCandidateProfile(
                request.getCandidateProfile()
        );

        reference.setReferenceName(
                request.getReferenceName()
        );

        reference.setReferenceEmail(
                request.getReferenceEmail()
        );

        reference.setRelation(
                request.getRelation()
        );

        reference.setReferenceText(
                referenceText.trim()
        );

        reference.setOrganizationName(
                organizationName == null
                        ? null
                        : organizationName.trim()
        );

        reference.setPositionTitle(
                positionTitle == null
                        ? null
                        : positionTitle.trim()
        );

        reference.setVisible(true);

        request.setStatus(
                ReferenceRequestStatus.ACCEPTED
        );

        request.setRespondedAt(
                LocalDateTime.now()
        );

        referenceRequestRepository.save(
                request
        );

        Reference saved = referenceRepository.save(reference);
        notificationService.create(request.getCandidateProfile().getUser().getId(), NotificationType.REFERENCE_REQUEST,
                "Reference received", request.getReferenceName() + " submitted a reference", "referenceId=" + saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public ReferenceRequest rejectReferenceRequest(
            String token
    ) {

        ReferenceRequest request =
                getPendingRequestByToken(token);

        request.setStatus(
                ReferenceRequestStatus.REJECTED
        );

        request.setRespondedAt(
                LocalDateTime.now()
        );

        return referenceRequestRepository.save(
                request
        );
    }

    @Override
    public ReferenceRequest getReferenceRequestById(
            Long id
    ) {

        return referenceRequestRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reference request not found"
                        )
                );
    }

    @Override
    public List<ReferenceRequest>
    getReferenceRequestsByCandidate(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return referenceRequestRepository
                .findByCandidateProfileIdOrderByCreatedAtDesc(
                        candidateProfileId
                );
    }

    @Override
    public Reference getReferenceById(
            Long id
    ) {

        return referenceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reference not found"
                        )
                );
    }

    @Override
    public List<Reference> getReferencesByCandidate(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return referenceRepository
                .findByCandidateProfileIdOrderByCreatedAtDesc(
                        candidateProfileId
                );
    }

    @Override
    public List<Reference> getVisibleReferencesByCandidate(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return referenceRepository
                .findByCandidateProfileIdAndVisibleTrueOrderByCreatedAtDesc(
                        candidateProfileId
                );
    }

    private ReferenceRequest getPendingRequestByToken(
            String token
    ) {

        if (token == null
                || token.isBlank()) {

            throw new BusinessException(
                    "Reference token cannot be empty"
            );
        }

        ReferenceRequest request =
                referenceRequestRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Reference request token not found"
                                )
                        );

        if (request.getStatus()
                != ReferenceRequestStatus.PENDING) {

            throw new BusinessException(
                    "Reference request is no longer pending"
            );
        }

        if (request.getExpiresAt() == null
                || request.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            request.setStatus(
                    ReferenceRequestStatus.EXPIRED
            );

            referenceRequestRepository.save(
                    request
            );

            throw new BusinessException(
                    "Reference request has expired"
            );
        }

        return request;
    }
}

package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Reference;
import com.ahmetemresanli.backend.entity.ReferenceRequest;
import com.ahmetemresanli.backend.enums.ReferenceRelation;

import java.util.List;

public interface IReferenceService {

    ReferenceRequest createReferenceRequest(
            Long candidateProfileId,
            String referenceName,
            String referenceEmail,
            ReferenceRelation relation,
            String requestMessage
    );

    Reference acceptReferenceRequest(
            String token,
            String referenceText,
            String organizationName,
            String positionTitle
    );

    ReferenceRequest rejectReferenceRequest(
            String token
    );

    ReferenceRequest getReferenceRequestById(
            Long id
    );

    List<ReferenceRequest> getReferenceRequestsByCandidate(
            Long candidateProfileId
    );

    Reference getReferenceById(
            Long id
    );

    List<Reference> getReferencesByCandidate(
            Long candidateProfileId
    );

    List<Reference> getVisibleReferencesByCandidate(
            Long candidateProfileId
    );
}
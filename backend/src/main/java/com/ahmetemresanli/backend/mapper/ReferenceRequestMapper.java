package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.ReferenceRequestResponse;
import com.ahmetemresanli.backend.entity.ReferenceRequest;

public final class ReferenceRequestMapper {

    private ReferenceRequestMapper() {
    }

    public static ReferenceRequestResponse toResponse(
            ReferenceRequest request
    ) {

        ReferenceRequestResponse response =
                new ReferenceRequestResponse();

        response.setId(request.getId());

        if (request.getCandidateProfile() != null) {
            response.setCandidateProfileId(
                    request.getCandidateProfile().getId()
            );
        }

        response.setReferenceName(
                request.getReferenceName()
        );

        response.setReferenceEmail(
                request.getReferenceEmail()
        );

        response.setRelation(
                request.getRelation()
        );

        response.setRequestMessage(
                request.getRequestMessage()
        );

        response.setStatus(
                request.getStatus()
        );

        response.setToken(
                request.getToken()
        );

        response.setExpiresAt(
                request.getExpiresAt()
        );

        response.setRespondedAt(
                request.getRespondedAt()
        );

        response.setCreatedAt(
                request.getCreatedAt()
        );

        response.setUpdatedAt(
                request.getUpdatedAt()
        );

        return response;
    }
}
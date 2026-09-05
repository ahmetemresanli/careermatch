package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.ReferenceResponse;
import com.ahmetemresanli.backend.entity.Reference;

public final class ReferenceMapper {

    private ReferenceMapper() {
    }

    public static ReferenceResponse toResponse(
            Reference reference
    ) {

        ReferenceResponse response =
                new ReferenceResponse();

        response.setId(reference.getId());

        if (reference.getReferenceRequest() != null) {
            response.setReferenceRequestId(
                    reference.getReferenceRequest().getId()
            );
        }

        if (reference.getCandidateProfile() != null) {
            response.setCandidateProfileId(
                    reference.getCandidateProfile().getId()
            );
        }

        response.setReferenceName(
                reference.getReferenceName()
        );

        response.setReferenceEmail(
                reference.getReferenceEmail()
        );

        response.setRelation(
                reference.getRelation()
        );

        response.setReferenceText(
                reference.getReferenceText()
        );

        response.setOrganizationName(
                reference.getOrganizationName()
        );

        response.setPositionTitle(
                reference.getPositionTitle()
        );

        response.setVisible(
                reference.isVisible()
        );

        response.setCreatedAt(
                reference.getCreatedAt()
        );

        response.setUpdatedAt(
                reference.getUpdatedAt()
        );

        return response;
    }
}
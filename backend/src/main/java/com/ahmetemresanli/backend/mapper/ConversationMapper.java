package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.ConversationResponse;
import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Conversation;

public final class ConversationMapper {

    private ConversationMapper() {
    }

    public static ConversationResponse toResponse(
            Conversation conversation
    ) {

        ConversationResponse response =
                new ConversationResponse();

        response.setId(
                conversation.getId()
        );

        CandidateProfile candidate =
                conversation.getCandidateProfile();

        if (candidate != null) {

            response.setCandidateProfileId(
                    candidate.getId()
            );

            String firstName =
                    candidate.getFirstName() == null
                            ? ""
                            : candidate.getFirstName();

            String lastName =
                    candidate.getLastName() == null
                            ? ""
                            : candidate.getLastName();

            response.setCandidateName(
                    (firstName + " " + lastName).trim()
            );
        }

        if (conversation.getCompany() != null) {

            response.setCompanyId(
                    conversation
                            .getCompany()
                            .getId()
            );

            response.setCompanyName(
                    conversation
                            .getCompany()
                            .getName()
            );
        }

        response.setActive(
                conversation.isActive()
        );

        response.setCreatedAt(
                conversation.getCreatedAt()
        );

        response.setUpdatedAt(
                conversation.getUpdatedAt()
        );

        return response;
    }
}
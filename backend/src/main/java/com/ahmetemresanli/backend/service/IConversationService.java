package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Conversation;

import java.util.List;

public interface IConversationService {

    Conversation createConversation(
            Long candidateProfileId,
            Long companyId
    );

    Conversation getConversationById(
            Long conversationId
    );

    List<Conversation> getConversationsByCandidate(
            Long candidateProfileId
    );

    List<Conversation> getConversationsByCompany(
            Long companyId
    );

    Conversation deactivateConversation(
            Long conversationId
    );
}
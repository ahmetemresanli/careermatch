package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.response.ConversationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IConversationController {

    ResponseEntity<ConversationResponse> createConversation(
            Long candidateProfileId,
            Long companyId
    );

    ResponseEntity<ConversationResponse> getConversationById(
            Long conversationId
    );

    ResponseEntity<List<ConversationResponse>>
    getConversationsByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<List<ConversationResponse>>
    getConversationsByCompany(
            Long companyId
    );

    ResponseEntity<ConversationResponse> deactivateConversation(
            Long conversationId
    );
}
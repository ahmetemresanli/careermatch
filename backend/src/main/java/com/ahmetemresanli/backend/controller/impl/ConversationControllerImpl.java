package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IConversationController;
import com.ahmetemresanli.backend.dto.response.ConversationResponse;
import com.ahmetemresanli.backend.entity.Conversation;
import com.ahmetemresanli.backend.mapper.ConversationMapper;
import com.ahmetemresanli.backend.service.IConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationControllerImpl
        implements IConversationController {

    private final IConversationService conversationService;

    public ConversationControllerImpl(
            IConversationService conversationService
    ) {
        this.conversationService = conversationService;
    }

    @Override
    @PostMapping(
            "/candidate/{candidateProfileId}/company/{companyId}"
    )
    public ResponseEntity<ConversationResponse> createConversation(
            @PathVariable Long candidateProfileId,
            @PathVariable Long companyId
    ) {

        Conversation conversation =
                conversationService.createConversation(
                        candidateProfileId,
                        companyId
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ConversationMapper.toResponse(
                                conversation
                        )
                );
    }

    @Override
    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationResponse> getConversationById(
            @PathVariable Long conversationId
    ) {

        Conversation conversation =
                conversationService
                        .getConversationById(
                                conversationId
                        );

        return ResponseEntity.ok(
                ConversationMapper.toResponse(
                        conversation
                )
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<ConversationResponse>>
    getConversationsByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<ConversationResponse> responses =
                conversationService
                        .getConversationsByCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(ConversationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ConversationResponse>>
    getConversationsByCompany(
            @PathVariable Long companyId
    ) {

        List<ConversationResponse> responses =
                conversationService
                        .getConversationsByCompany(
                                companyId
                        )
                        .stream()
                        .map(ConversationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @PutMapping("/{conversationId}/deactivate")
    public ResponseEntity<ConversationResponse> deactivateConversation(
            @PathVariable Long conversationId
    ) {

        Conversation conversation =
                conversationService
                        .deactivateConversation(
                                conversationId
                        );

        return ResponseEntity.ok(
                ConversationMapper.toResponse(
                        conversation
                )
        );
    }
}
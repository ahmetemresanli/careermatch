package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IMessageController;
import com.ahmetemresanli.backend.dto.request.MessageCreateRequest;
import com.ahmetemresanli.backend.dto.response.MessageResponse;
import com.ahmetemresanli.backend.dto.response.UnreadMessageCountResponse;
import com.ahmetemresanli.backend.entity.Message;
import com.ahmetemresanli.backend.mapper.MessageMapper;
import com.ahmetemresanli.backend.service.IMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.ahmetemresanli.backend.security.AccessControlService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageControllerImpl
        implements IMessageController {

    private final IMessageService messageService;
    private final AccessControlService access;

    public MessageControllerImpl(
            IMessageService messageService,
            AccessControlService access
    ) {
        this.messageService = messageService;
        this.access = access;
    }

    @Override
    @PostMapping("/conversation/{conversationId}")
    @PreAuthorize("@access.canAccessConversation(#conversationId)")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable Long conversationId,
            @Valid @RequestBody MessageCreateRequest request
    ) {

        Message message =
                messageService.sendMessage(
                        conversationId,
                        access.currentUserId(),
                        request.getContent()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        MessageMapper.toResponse(
                                message
                        )
                );
    }

    @Override
    @GetMapping("/{messageId}")
    @PreAuthorize("@access.canAccessMessage(#messageId)")
    public ResponseEntity<MessageResponse> getMessageById(
            @PathVariable Long messageId
    ) {

        Message message =
                messageService.getMessageById(
                        messageId
                );

        return ResponseEntity.ok(
                MessageMapper.toResponse(
                        message
                )
        );
    }

    @Override
    @GetMapping("/conversation/{conversationId}")
    @PreAuthorize("@access.canAccessConversation(#conversationId)")
    public ResponseEntity<List<MessageResponse>>
    getMessagesByConversation(
            @PathVariable Long conversationId
    ) {

        List<MessageResponse> responses =
                messageService
                        .getMessagesByConversation(
                                conversationId
                        )
                        .stream()
                        .map(MessageMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/conversation/{conversationId}/unread")
    @PreAuthorize("@access.canAccessConversation(#conversationId)")
    public ResponseEntity<List<MessageResponse>>
    getUnreadMessages(
            @PathVariable Long conversationId
    ) {

        List<MessageResponse> responses =
                messageService
                        .getUnreadMessages(
                                conversationId,
                                access.currentUserId()
                        )
                        .stream()
                        .map(MessageMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/conversation/{conversationId}/unread-count")
    @PreAuthorize("@access.canAccessConversation(#conversationId)")
    public ResponseEntity<UnreadMessageCountResponse>
    getUnreadMessageCount(
            @PathVariable Long conversationId
    ) {

        long unreadCount =
                messageService
                        .getUnreadMessageCount(
                                conversationId,
                                access.currentUserId()
                        );

        UnreadMessageCountResponse response =
                new UnreadMessageCountResponse(
                        conversationId,
                        access.currentUserId(),
                        unreadCount
                );

        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{messageId}/read")
    @PreAuthorize("@access.canAccessMessage(#messageId)")
    public ResponseEntity<MessageResponse> markMessageAsRead(
            @PathVariable Long messageId
    ) {

        Message message =
                messageService.markMessageAsRead(
                        messageId,
                        access.currentUserId()
                );

        return ResponseEntity.ok(
                MessageMapper.toResponse(
                        message
                )
        );
    }
}

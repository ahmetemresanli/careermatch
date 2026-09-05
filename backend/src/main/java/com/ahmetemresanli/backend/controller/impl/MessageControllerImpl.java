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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageControllerImpl
        implements IMessageController {

    private final IMessageService messageService;

    public MessageControllerImpl(
            IMessageService messageService
    ) {
        this.messageService = messageService;
    }

    @Override
    @PostMapping("/conversation/{conversationId}")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable Long conversationId,
            @Valid @RequestBody MessageCreateRequest request
    ) {

        Message message =
                messageService.sendMessage(
                        conversationId,
                        request.getSenderUserId(),
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
    public ResponseEntity<List<MessageResponse>>
    getUnreadMessages(
            @PathVariable Long conversationId,
            @RequestParam Long readerUserId
    ) {

        List<MessageResponse> responses =
                messageService
                        .getUnreadMessages(
                                conversationId,
                                readerUserId
                        )
                        .stream()
                        .map(MessageMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/conversation/{conversationId}/unread-count")
    public ResponseEntity<UnreadMessageCountResponse>
    getUnreadMessageCount(
            @PathVariable Long conversationId,
            @RequestParam Long readerUserId
    ) {

        long unreadCount =
                messageService
                        .getUnreadMessageCount(
                                conversationId,
                                readerUserId
                        );

        UnreadMessageCountResponse response =
                new UnreadMessageCountResponse(
                        conversationId,
                        readerUserId,
                        unreadCount
                );

        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{messageId}/read")
    public ResponseEntity<MessageResponse> markMessageAsRead(
            @PathVariable Long messageId,
            @RequestParam Long readerUserId
    ) {

        Message message =
                messageService.markMessageAsRead(
                        messageId,
                        readerUserId
                );

        return ResponseEntity.ok(
                MessageMapper.toResponse(
                        message
                )
        );
    }
}
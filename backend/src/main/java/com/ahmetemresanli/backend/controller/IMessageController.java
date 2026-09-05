package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.MessageCreateRequest;
import com.ahmetemresanli.backend.dto.response.MessageResponse;
import com.ahmetemresanli.backend.dto.response.UnreadMessageCountResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMessageController {

    ResponseEntity<MessageResponse> sendMessage(
            Long conversationId,
            MessageCreateRequest request
    );

    ResponseEntity<MessageResponse> getMessageById(
            Long messageId
    );

    ResponseEntity<List<MessageResponse>>
    getMessagesByConversation(
            Long conversationId
    );

    ResponseEntity<List<MessageResponse>>
    getUnreadMessages(
            Long conversationId
    );

    ResponseEntity<UnreadMessageCountResponse>
    getUnreadMessageCount(
            Long conversationId
    );

    ResponseEntity<MessageResponse> markMessageAsRead(
            Long messageId
    );
}

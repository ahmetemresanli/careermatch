package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Message;

import java.util.List;

public interface IMessageService {

    Message sendMessage(
            Long conversationId,
            Long senderUserId,
            String content
    );

    Message getMessageById(
            Long messageId
    );

    List<Message> getMessagesByConversation(
            Long conversationId
    );

    List<Message> getUnreadMessages(
            Long conversationId,
            Long readerUserId
    );

    long getUnreadMessageCount(
            Long conversationId,
            Long readerUserId
    );

    Message markMessageAsRead(
            Long messageId,
            Long readerUserId
    );
}
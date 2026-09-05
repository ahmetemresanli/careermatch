package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository
        extends JpaRepository<Message, Long> {

    List<Message>
    findByConversationIdOrderByCreatedAtAsc(
            Long conversationId
    );

    List<Message>
    findByConversationIdAndReadFalseOrderByCreatedAtAsc(
            Long conversationId
    );

    long countByConversationIdAndReadFalse(
            Long conversationId
    );

    List<Message>
    findByConversationIdAndSenderIdOrderByCreatedAtAsc(
            Long conversationId,
            Long senderUserId
    );

    List<Message>
    findByConversationIdAndReadFalseAndSenderIdNotOrderByCreatedAtAsc(
            Long conversationId,
            Long senderUserId
    );

    long countByConversationIdAndReadFalseAndSenderIdNot(
            Long conversationId,
            Long senderUserId
    );
}
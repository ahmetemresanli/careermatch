package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.MessageResponse;
import com.ahmetemresanli.backend.entity.Message;

public final class MessageMapper {

    private MessageMapper() {
    }

    public static MessageResponse toResponse(
            Message message
    ) {

        MessageResponse response =
                new MessageResponse();

        response.setId(
                message.getId()
        );

        if (message.getConversation() != null) {

            response.setConversationId(
                    message
                            .getConversation()
                            .getId()
            );
        }

        if (message.getSender() != null) {

            response.setSenderUserId(
                    message
                            .getSender()
                            .getId()
            );

            response.setSenderEmail(
                    message
                            .getSender()
                            .getEmail()
            );

            response.setSenderRole(
                    message
                            .getSender()
                            .getRole()
            );
        }

        response.setContent(
                message.getContent()
        );

        response.setRead(
                message.isRead()
        );

        response.setReadAt(
                message.getReadAt()
        );

        response.setCreatedAt(
                message.getCreatedAt()
        );

        return response;
    }
}
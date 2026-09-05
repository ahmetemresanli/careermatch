package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CompanyMember;
import com.ahmetemresanli.backend.entity.Conversation;
import com.ahmetemresanli.backend.entity.Message;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.ConversationRepository;
import com.ahmetemresanli.backend.repository.MessageRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.IMessageService;
import com.ahmetemresanli.backend.service.INotificationService;
import com.ahmetemresanli.backend.enums.NotificationType;
import com.ahmetemresanli.backend.repository.CompanyMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl
        implements IMessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final INotificationService notificationService;

    public MessageServiceImpl(
            MessageRepository messageRepository,
            ConversationRepository conversationRepository,
            UserRepository userRepository,
            CompanyMemberRepository companyMemberRepository,
            INotificationService notificationService
    ) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.companyMemberRepository = companyMemberRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public Message sendMessage(
            Long conversationId,
            Long senderUserId,
            String content
    ) {

        Conversation conversation =
                getConversation(conversationId);

        if (!conversation.isActive()) {
            throw new BusinessException(
                    "Cannot send a message to an inactive conversation"
            );
        }

        User sender =
                userRepository
                        .findById(senderUserId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Sender user not found"
                                )
                        );

        if (!sender.isActive()) {
            throw new BusinessException(
                    "Inactive users cannot send messages"
            );
        }

        /*
         * Gönderen kişi gerçekten bu
         * conversation'ın taraflarından biri mi?
         */
        validateConversationParticipant(
                conversation,
                senderUserId
        );

        if (content == null
                || content.isBlank()) {

            throw new BusinessException(
                    "Message content cannot be empty"
            );
        }

        String normalizedContent =
                content.trim();

        if (normalizedContent.length() > 5000) {

            throw new BusinessException(
                    "Message content cannot exceed 5000 characters"
            );
        }

        Message message = new Message();

        message.setConversation(
                conversation
        );

        message.setSender(
                sender
        );

        message.setContent(
                normalizedContent
        );

        message.setRead(false);
        message.setReadAt(null);

        Message savedMessage =
                messageRepository.save(message);

        /*
         * Yeni mesaj geldiği için conversation'ın
         * updatedAt değerini güncelliyoruz.
         *
         * Böylece konuşma listesinde en yeni
         * konuşma yukarı çıkar.
         */
        conversation.touch();

        conversationRepository.save(
                conversation
        );

        Long candidateUserId = conversation.getCandidateProfile().getUser().getId();
        if (!candidateUserId.equals(senderUserId)) {
            notificationService.create(candidateUserId, NotificationType.MESSAGE, "New message",
                    "You have a new message from " + conversation.getCompany().getName(),
                    "conversationId=" + conversationId);
        } else {
            companyMemberRepository.findByCompanyId(conversation.getCompany().getId()).stream()
                    .filter(member -> member.isActive() && !member.getUser().getId().equals(senderUserId))
                    .forEach(member -> notificationService.create(member.getUser().getId(), NotificationType.MESSAGE,
                            "New message", "You have a new candidate message", "conversationId=" + conversationId));
        }

        return savedMessage;
    }

    @Override
    public Message getMessageById(
            Long messageId
    ) {

        return messageRepository
                .findById(messageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Message not found"
                        )
                );
    }

    @Override
    public List<Message>
    getMessagesByConversation(
            Long conversationId
    ) {

        getConversation(conversationId);

        return messageRepository
                .findByConversationIdOrderByCreatedAtAsc(
                        conversationId
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getUnreadMessages(
            Long conversationId,
            Long readerUserId
    ) {

        Conversation conversation =
                getConversation(conversationId);

        validateConversationParticipant(
                conversation,
                readerUserId
        );

        return messageRepository
                .findByConversationIdAndReadFalseAndSenderIdNotOrderByCreatedAtAsc(
                        conversationId,
                        readerUserId
                );
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadMessageCount(
            Long conversationId,
            Long readerUserId
    ) {

        Conversation conversation =
                getConversation(conversationId);

        validateConversationParticipant(
                conversation,
                readerUserId
        );

        return messageRepository
                .countByConversationIdAndReadFalseAndSenderIdNot(
                        conversationId,
                        readerUserId
                );
    }

    @Override
    @Transactional
    public Message markMessageAsRead(
            Long messageId,
            Long readerUserId
    ) {

        Message message =
                getMessageById(messageId);

        Conversation conversation =
                message.getConversation();

        validateConversationParticipant(
                conversation,
                readerUserId
        );

        /*
         * Mesajı gönderen kişi kendi mesajını
         * "read" olarak işaretleyemez.
         */
        if (message.getSender()
                .getId()
                .equals(readerUserId)) {

            throw new BusinessException(
                    "Sender cannot mark their own message as read"
            );
        }

        /*
         * Zaten okunmuşsa tekrar DB update
         * yapmamıza gerek yok.
         */
        if (message.isRead()) {
            return message;
        }

        message.setRead(true);

        message.setReadAt(
                LocalDateTime.now()
        );

        return messageRepository.save(
                message
        );
    }

    private Conversation getConversation(
            Long conversationId
    ) {

        return conversationRepository
                .findById(conversationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Conversation not found"
                        )
                );
    }

    private void validateConversationParticipant(
            Conversation conversation,
            Long userId
    ) {

        if (userId == null) {
            throw new BusinessException(
                    "User id cannot be null"
            );
        }

        /*
         * Candidate tarafı mı?
         */
        boolean candidateParticipant =
                conversation
                        .getCandidateProfile()
                        .getUser() != null
                        &&
                        conversation
                                .getCandidateProfile()
                                .getUser()
                                .getId()
                                .equals(userId);

        /*
         * Company tarafındaki aktif
         * CompanyMember'lardan biri mi?
         */
        boolean companyParticipant =
                conversation
                        .getCompany()
                        .getMembers()
                        .stream()
                        .filter(CompanyMember::isActive)
                        .anyMatch(member ->
                                member.getUser() != null
                                        &&
                                        member
                                                .getUser()
                                                .getId()
                                                .equals(userId)
                        );

        if (!candidateParticipant
                && !companyParticipant) {

            throw new BusinessException(
                    "User is not a participant of this conversation"
            );
        }
    }
}

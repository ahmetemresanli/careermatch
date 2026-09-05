package com.ahmetemresanli.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Mesaj hangi konuşmaya ait?
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "conversation_id",
            nullable = false
    )
    private Conversation conversation;

    /*
     * Mesajı hangi User gönderdi?
     *
     * Candidate ise adayın User kaydı.
     *
     * Company tarafı ise CompanyMember'a
     * bağlı User kaydı.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "sender_user_id",
            nullable = false
    )
    private User sender;

    /*
     * Mesaj içeriği.
     */
    @Column(
            nullable = false,
            length = 5000
    )
    private String content;

    /*
     * Karşı taraf mesajı okudu mu?
     */
    @Column(nullable = false)
    private boolean read = false;

    /*
     * Mesajın okunduğu tarih.
     *
     * read = false ise null.
     */
    private LocalDateTime readAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(
            Conversation conversation
    ) {
        this.conversation = conversation;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(
            User sender
    ) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(
            String content
    ) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(
            boolean read
    ) {
        this.read = read;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(
            LocalDateTime readAt
    ) {
        this.readAt = readAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
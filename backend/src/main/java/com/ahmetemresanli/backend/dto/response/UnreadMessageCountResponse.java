package com.ahmetemresanli.backend.dto.response;

public class UnreadMessageCountResponse {

    private Long conversationId;
    private Long readerUserId;
    private long unreadCount;

    public UnreadMessageCountResponse() {
    }

    public UnreadMessageCountResponse(
            Long conversationId,
            Long readerUserId,
            long unreadCount
    ) {
        this.conversationId = conversationId;
        this.readerUserId = readerUserId;
        this.unreadCount = unreadCount;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getReaderUserId() {
        return readerUserId;
    }

    public void setReaderUserId(Long readerUserId) {
        this.readerUserId = readerUserId;
    }

    public long getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(long unreadCount) {
        this.unreadCount = unreadCount;
    }
}
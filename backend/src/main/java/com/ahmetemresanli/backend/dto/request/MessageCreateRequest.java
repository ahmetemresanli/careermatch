package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MessageCreateRequest {

    /*
     * Security/JWT şu an olmadığı için
     * gönderen user id'yi request'ten alıyoruz.
     *
     * Security geldiğinde bunu body'den kaldıracağız.
     */
    @NotNull(message = "Sender user id cannot be null")
    private Long senderUserId;

    @NotBlank(message = "Message content cannot be empty")
    @Size(
            max = 5000,
            message = "Message content cannot exceed 5000 characters"
    )
    private String content;

    public MessageCreateRequest() {
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageCreateRequest {

    @NotBlank(message = "Message content cannot be empty")
    @Size(
            max = 5000,
            message = "Message content cannot exceed 5000 characters"
    )
    private String content;

    public MessageCreateRequest() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

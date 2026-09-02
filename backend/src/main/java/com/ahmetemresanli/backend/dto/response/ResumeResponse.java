package com.ahmetemresanli.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResumeResponse {

    private Long id;

    private Long candidateProfileId;

    private String fileName;

    private String fileUrl;

    private String contentType;

    private boolean defaultResume;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
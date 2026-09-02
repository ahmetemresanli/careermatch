package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.ApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationResponse {

    private Long id;

    private Long candidateProfileId;

    private String candidateFirstName;

    private String candidateLastName;

    private Long jobPostingId;

    private String jobTitle;

    private Long companyId;

    private String companyName;

    private Long resumeId;

    private String resumeFileName;

    private ApplicationStatus status;

    private String coverLetter;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationCreateRequest {

    @Positive(message = "Resume ID must be greater than zero")
    private Long resumeId;

    @Size(
            max = 3000,
            message = "Cover letter cannot exceed 3000 characters"
    )
    private String coverLetter;
}
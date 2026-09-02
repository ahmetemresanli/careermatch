package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResumeCreateRequest {

    @NotBlank(message = "File name cannot be empty")
    @Size(
            max = 255,
            message = "File name cannot exceed 255 characters"
    )
    private String fileName;

    @NotBlank(message = "File URL cannot be empty")
    @Size(
            max = 1000,
            message = "File URL cannot exceed 1000 characters"
    )
    private String fileUrl;

    @Size(
            max = 100,
            message = "Content type cannot exceed 100 characters"
    )
    private String contentType;

    private Boolean defaultResume;
}
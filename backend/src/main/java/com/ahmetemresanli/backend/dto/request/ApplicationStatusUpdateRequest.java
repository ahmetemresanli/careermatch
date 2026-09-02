package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationStatusUpdateRequest {

    @NotNull(message = "Application status cannot be null")
    private ApplicationStatus status;
}
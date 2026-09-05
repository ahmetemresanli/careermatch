package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SecurityAnswerRequest(@NotBlank @Size(min = 2, max = 200) String answer) { }

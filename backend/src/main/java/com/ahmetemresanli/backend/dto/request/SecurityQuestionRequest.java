package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SecurityQuestionRequest(@NotBlank @Size(max = 300) String question) { }

package com.ahmetemresanli.backend.dto.request;
import jakarta.validation.constraints.*;
public record LanguageRequest(@NotBlank @Size(max=100) String name) { }

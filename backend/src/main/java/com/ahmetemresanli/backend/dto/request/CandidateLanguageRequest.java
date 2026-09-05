package com.ahmetemresanli.backend.dto.request;
import com.ahmetemresanli.backend.enums.LanguageProficiency; import jakarta.validation.constraints.NotNull;
public record CandidateLanguageRequest(@NotNull Long languageId, @NotNull LanguageProficiency proficiency) { }

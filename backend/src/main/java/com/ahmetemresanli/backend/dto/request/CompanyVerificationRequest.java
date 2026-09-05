package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public record CompanyVerificationRequest(@NotNull Boolean verified) { }

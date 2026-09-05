package com.ahmetemresanli.backend.dto.request;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record CertificateRequest(@NotBlank @Size(max=200) String name, @NotBlank @Size(max=200) String issuer,
                                 LocalDate issueDate, LocalDate expiryDate, @Size(max=255) String credentialId,
                                 @Size(max=1000) String credentialUrl) { }

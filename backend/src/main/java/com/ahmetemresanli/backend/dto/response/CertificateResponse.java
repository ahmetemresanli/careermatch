package com.ahmetemresanli.backend.dto.response;
import java.time.*;
public record CertificateResponse(Long id,Long candidateProfileId,String name,String issuer,LocalDate issueDate,LocalDate expiryDate,String credentialId,String credentialUrl,LocalDateTime createdAt,LocalDateTime updatedAt) { }

package com.ahmetemresanli.backend.dto.response;
import java.time.*;
public record ProjectResponse(Long id,Long candidateProfileId,String name,String description,String projectUrl,String repositoryUrl,LocalDate startDate,LocalDate endDate,LocalDateTime createdAt,LocalDateTime updatedAt) { }

package com.ahmetemresanli.backend.dto.response;
import java.time.LocalDateTime;
public record FavoriteResponse(Long id,Long candidateProfileId,Long jobPostingId,String jobTitle,LocalDateTime createdAt) { }

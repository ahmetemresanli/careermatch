package com.ahmetemresanli.backend.dto.request;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record ProjectRequest(@NotBlank @Size(max=200) String name, @Size(max=3000) String description,
                             @Size(max=1000) String projectUrl, @Size(max=1000) String repositoryUrl,
                             LocalDate startDate, LocalDate endDate) { }

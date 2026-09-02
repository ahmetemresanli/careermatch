package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.JobSearchStatus;
import com.ahmetemresanli.backend.enums.WorkModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CandidateProfileResponse {

    private Long id;

    private Long userId;

    private String firstName;

    private String lastName;

    private String about;

    private String city;

    private String country;

    private String githubUrl;

    private String linkedinUrl;

    private String websiteUrl;

    private JobSearchStatus jobSearchStatus;

    private boolean visibleToRecruiters;

    private BigDecimal expectedMinSalary;

    private BigDecimal expectedMaxSalary;

    private WorkModel preferredWorkModel;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
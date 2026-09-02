package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.enums.WorkModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JobPostingResponse {

    private Long id;

    private Long companyId;

    private String companyName;

    private String title;

    private String description;

    private String city;

    private String country;

    private EmploymentType employmentType;

    private WorkModel workModel;

    private JobLevel jobLevel;

    private Integer minimumExperienceYears;

    private BigDecimal minimumSalary;

    private BigDecimal maximumSalary;

    private JobStatus status;

    private LocalDateTime applicationDeadline;

    private List<JobSkillResponse> skills = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
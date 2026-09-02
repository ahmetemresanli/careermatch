package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.JobSearchStatus;
import com.ahmetemresanli.backend.enums.WorkModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CandidateProfileCreateRequest {

    @NotBlank(message = "First name cannot be empty")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @Size(max = 2000, message = "About cannot exceed 2000 characters")
    private String about;

    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @Size(max = 100, message = "Country cannot exceed 100 characters")
    private String country;

    @Size(max = 500, message = "Github URL cannot exceed 500 characters")
    private String githubUrl;

    @Size(max = 500, message = "LinkedIn URL cannot exceed 500 characters")
    private String linkedinUrl;

    @Size(max = 500, message = "Website URL cannot exceed 500 characters")
    private String websiteUrl;

    private JobSearchStatus jobSearchStatus;

    private Boolean visibleToRecruiters;

    @PositiveOrZero(
            message = "Expected minimum salary cannot be negative"
    )
    private BigDecimal expectedMinSalary;

    @PositiveOrZero(
            message = "Expected maximum salary cannot be negative"
    )
    private BigDecimal expectedMaxSalary;

    private WorkModel preferredWorkModel;
}
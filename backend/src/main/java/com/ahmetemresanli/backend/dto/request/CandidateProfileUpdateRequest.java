package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.JobSearchStatus;
import com.ahmetemresanli.backend.enums.WorkModel;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CandidateProfileUpdateRequest {

    @Size(
            min = 1,
            max = 100,
            message = "First name must be between 1 and 100 characters"
    )
    private String firstName;

    @Size(
            min = 1,
            max = 100,
            message = "Last name must be between 1 and 100 characters"
    )
    private String lastName;

    @Size(max = 2000)
    private String about;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String country;

    @Size(max = 500)
    private String githubUrl;

    @Size(max = 500)
    private String linkedinUrl;

    @Size(max = 500)
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
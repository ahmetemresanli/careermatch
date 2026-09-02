package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class JobPostingCreateRequest {

    @NotBlank(message = "Job title cannot be empty")
    @Size(
            max = 200,
            message = "Job title cannot exceed 200 characters"
    )
    private String title;

    @NotBlank(message = "Job description cannot be empty")
    private String description;

    @Size(
            max = 100,
            message = "City cannot exceed 100 characters"
    )
    private String city;

    @Size(
            max = 100,
            message = "Country cannot exceed 100 characters"
    )
    private String country;

    @NotNull(message = "Employment type cannot be null")
    private EmploymentType employmentType;

    @NotNull(message = "Work model cannot be null")
    private WorkModel workModel;

    @NotNull(message = "Job level cannot be null")
    private JobLevel jobLevel;

    @PositiveOrZero(
            message = "Minimum experience years cannot be negative"
    )
    private Integer minimumExperienceYears;

    @PositiveOrZero(
            message = "Minimum salary cannot be negative"
    )
    private BigDecimal minimumSalary;

    @PositiveOrZero(
            message = "Maximum salary cannot be negative"
    )
    private BigDecimal maximumSalary;

    @Future(
            message = "Application deadline must be in the future"
    )
    private LocalDateTime applicationDeadline;
}
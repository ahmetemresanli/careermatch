package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyCreateRequest {

    @NotBlank(message = "Company name cannot be empty")
    @Size(
            max = 200,
            message = "Company name cannot exceed 200 characters"
    )
    private String name;

    @Size(
            max = 2000,
            message = "Description cannot exceed 2000 characters"
    )
    private String description;

    @Size(
            max = 150,
            message = "Industry cannot exceed 150 characters"
    )
    private String industry;

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

    @Size(
            max = 500,
            message = "Website URL cannot exceed 500 characters"
    )
    private String websiteUrl;

    @Size(
            max = 500,
            message = "Logo URL cannot exceed 500 characters"
    )
    private String logoUrl;

    @NotBlank(message = "Company domain cannot be empty")
    @Size(
            max = 255,
            message = "Domain cannot exceed 255 characters"
    )
    private String domain;

    @PositiveOrZero(
            message = "Employee count cannot be negative"
    )
    private Integer employeeCount;
}
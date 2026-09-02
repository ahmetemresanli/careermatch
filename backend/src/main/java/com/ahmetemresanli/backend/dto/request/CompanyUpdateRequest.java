package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyUpdateRequest {

    @Size(
            min = 1,
            max = 200,
            message = "Company name must be between 1 and 200 characters"
    )
    private String name;

    @Size(max = 2000)
    private String description;

    @Size(max = 150)
    private String industry;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String country;

    @Size(max = 500)
    private String websiteUrl;

    @Size(max = 500)
    private String logoUrl;

    @Size(
            min = 1,
            max = 255,
            message = "Domain must be between 1 and 255 characters"
    )
    private String domain;

    @PositiveOrZero(
            message = "Employee count cannot be negative"
    )
    private Integer employeeCount;
}
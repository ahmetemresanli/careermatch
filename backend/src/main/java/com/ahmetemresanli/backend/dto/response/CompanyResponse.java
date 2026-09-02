package com.ahmetemresanli.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CompanyResponse {

    private Long id;

    private String name;

    private String description;

    private String industry;

    private String city;

    private String country;

    private String websiteUrl;

    private String logoUrl;

    private String domain;

    private Integer employeeCount;

    private boolean verified;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
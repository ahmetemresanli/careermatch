package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ExperienceCreateRequest {

    @NotBlank(message = "Company name cannot be empty")
    @Size(max = 200, message = "Company name cannot exceed 200 characters")
    private String companyName;

    @NotBlank(message = "Position title cannot be empty")
    @Size(max = 200, message = "Position title cannot exceed 200 characters")
    private String positionTitle;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Currently working information cannot be null")
    private Boolean currentlyWorking;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    public ExperienceCreateRequest() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getCurrentlyWorking() {
        return currentlyWorking;
    }

    public void setCurrentlyWorking(Boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
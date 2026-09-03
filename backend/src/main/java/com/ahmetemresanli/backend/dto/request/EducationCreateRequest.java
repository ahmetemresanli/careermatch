package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.EducationLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class EducationCreateRequest {

    @NotBlank(message = "University name cannot be empty")
    @Size(max = 200, message = "University name cannot exceed 200 characters")
    private String universityName;

    @NotBlank(message = "Department cannot be empty")
    @Size(max = 200, message = "Department cannot exceed 200 characters")
    private String department;

    @NotNull(message = "Education level cannot be null")
    private EducationLevel educationLevel;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Currently studying information cannot be null")
    private Boolean currentlyStudying;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    public EducationCreateRequest() {
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
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

    public Boolean getCurrentlyStudying() {
        return currentlyStudying;
    }

    public void setCurrentlyStudying(Boolean currentlyStudying) {
        this.currentlyStudying = currentlyStudying;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.*;
import com.ahmetemresanli.backend.dto.response.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface IAdminController {
    ResponseEntity<List<UserResponse>> users();
    ResponseEntity<UserResponse> setUserActive(Long id, ActiveStatusRequest request);
    ResponseEntity<List<CompanyResponse>> companies();
    ResponseEntity<CompanyResponse> verifyCompany(Long id, CompanyVerificationRequest request);
    ResponseEntity<CompanyResponse> setCompanyActive(Long id, ActiveStatusRequest request);
    ResponseEntity<JobPostingResponse> closeJob(Long id);
    ResponseEntity<List<EducationVerificationResponse>> pendingEducation();
    ResponseEntity<List<EmploymentVerificationResponse>> pendingEmployment();
}

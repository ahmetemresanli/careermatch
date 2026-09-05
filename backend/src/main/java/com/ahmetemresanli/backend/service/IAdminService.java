package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.dto.response.*;
import java.util.List;

public interface IAdminService {
    List<UserResponse> users();
    UserResponse setUserActive(Long userId, boolean active);
    List<CompanyResponse> companies();
    CompanyResponse setCompanyVerified(Long companyId, boolean verified);
    CompanyResponse setCompanyActive(Long companyId, boolean active);
    JobPostingResponse closeJob(Long jobId);
    List<EducationVerificationResponse> pendingEducationVerifications();
    List<EmploymentVerificationResponse> pendingEmploymentVerifications();
}

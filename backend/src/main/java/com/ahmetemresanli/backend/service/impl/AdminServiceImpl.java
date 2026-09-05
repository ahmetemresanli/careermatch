package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.dto.response.*;
import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.mapper.*;
import com.ahmetemresanli.backend.repository.*;
import com.ahmetemresanli.backend.security.AccessControlService;
import com.ahmetemresanli.backend.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {
    private final UserRepository users; private final CompanyRepository companies;
    private final EducationVerificationRepository educationVerifications;
    private final EmploymentVerificationRepository employmentVerifications;
    private final IJobPostingService jobs; private final IAuditLogService audit;
    private final AccessControlService access;
    public AdminServiceImpl(UserRepository users, CompanyRepository companies,
                            EducationVerificationRepository educationVerifications,
                            EmploymentVerificationRepository employmentVerifications,
                            IJobPostingService jobs, IAuditLogService audit, AccessControlService access) {
        this.users = users; this.companies = companies; this.educationVerifications = educationVerifications;
        this.employmentVerifications = employmentVerifications; this.jobs = jobs; this.audit = audit; this.access = access;
    }
    @Override public List<UserResponse> users() { return users.findAll().stream().map(UserMapper::toResponse).toList(); }
    @Override @Transactional public UserResponse setUserActive(Long id, boolean active) {
        if (access.currentUserId().equals(id) && !active) throw new BusinessException("Admin cannot deactivate their own account");
        User user = users.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(active); users.save(user);
        audit.record(access.currentUserId(), "USER_ACTIVE_CHANGED", "User", id, "active=" + active);
        return UserMapper.toResponse(user);
    }
    @Override public List<CompanyResponse> companies() { return companies.findAll().stream().map(CompanyMapper::toResponse).toList(); }
    @Override @Transactional public CompanyResponse setCompanyVerified(Long id, boolean verified) {
        Company company = company(id); company.setVerified(verified); companies.save(company);
        audit.record(access.currentUserId(), "COMPANY_VERIFICATION_CHANGED", "Company", id, "verified=" + verified);
        return CompanyMapper.toResponse(company);
    }
    @Override @Transactional public CompanyResponse setCompanyActive(Long id, boolean active) {
        Company company = company(id); company.setActive(active); companies.save(company);
        audit.record(access.currentUserId(), "COMPANY_ACTIVE_CHANGED", "Company", id, "active=" + active);
        return CompanyMapper.toResponse(company);
    }
    @Override public JobPostingResponse closeJob(Long id) {
        var job = jobs.getJobPostingById(id);
        if (job.getStatus() != JobStatus.CLOSED) job = jobs.updateStatus(id, JobStatus.CLOSED);
        audit.record(access.currentUserId(), "JOB_CLOSED", "JobPosting", id, "admin moderation");
        return JobPostingMapper.toResponse(job);
    }
    @Override public List<EducationVerificationResponse> pendingEducationVerifications() {
        return educationVerifications.findByStatusOrderByCreatedAtAsc(VerificationStatus.PENDING).stream()
                .map(EducationVerificationMapper::toResponse).toList();
    }
    @Override public List<EmploymentVerificationResponse> pendingEmploymentVerifications() {
        return employmentVerifications.findByStatusOrderByCreatedAtAsc(VerificationStatus.PENDING).stream()
                .map(EmploymentVerificationMapper::toResponse).toList();
    }
    private Company company(Long id) { return companies.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found")); }
}

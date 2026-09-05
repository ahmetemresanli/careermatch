package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IAdminController;
import com.ahmetemresanli.backend.dto.request.*;
import com.ahmetemresanli.backend.dto.response.*;
import com.ahmetemresanli.backend.service.IAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/admin")
public class AdminControllerImpl implements IAdminController {
    private final IAdminService service; public AdminControllerImpl(IAdminService service) { this.service = service; }
    @Override @GetMapping("/users") public ResponseEntity<List<UserResponse>> users() { return ResponseEntity.ok(service.users()); }
    @Override @PutMapping("/users/{id}/active") public ResponseEntity<UserResponse> setUserActive(@PathVariable Long id, @Valid @RequestBody ActiveStatusRequest r) { return ResponseEntity.ok(service.setUserActive(id, r.active())); }
    @Override @GetMapping("/companies") public ResponseEntity<List<CompanyResponse>> companies() { return ResponseEntity.ok(service.companies()); }
    @Override @PutMapping("/companies/{id}/verification") public ResponseEntity<CompanyResponse> verifyCompany(@PathVariable Long id, @Valid @RequestBody CompanyVerificationRequest r) { return ResponseEntity.ok(service.setCompanyVerified(id, r.verified())); }
    @Override @PutMapping("/companies/{id}/active") public ResponseEntity<CompanyResponse> setCompanyActive(@PathVariable Long id, @Valid @RequestBody ActiveStatusRequest r) { return ResponseEntity.ok(service.setCompanyActive(id, r.active())); }
    @Override @PutMapping("/jobs/{id}/close") public ResponseEntity<JobPostingResponse> closeJob(@PathVariable Long id) { return ResponseEntity.ok(service.closeJob(id)); }
    @Override @GetMapping("/verifications/education/pending") public ResponseEntity<List<EducationVerificationResponse>> pendingEducation() { return ResponseEntity.ok(service.pendingEducationVerifications()); }
    @Override @GetMapping("/verifications/employment/pending") public ResponseEntity<List<EmploymentVerificationResponse>> pendingEmployment() { return ResponseEntity.ok(service.pendingEmploymentVerifications()); }
}

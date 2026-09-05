package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICompanyController;
import com.ahmetemresanli.backend.dto.request.CompanyCreateRequest;
import com.ahmetemresanli.backend.dto.request.CompanyUpdateRequest;
import com.ahmetemresanli.backend.dto.response.CompanyResponse;
import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.mapper.CompanyMapper;
import com.ahmetemresanli.backend.service.ICompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyControllerImpl
        implements ICompanyController {

    private final ICompanyService companyService;

    public CompanyControllerImpl(
            ICompanyService companyService
    ) {
        this.companyService = companyService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('COMPANY','ADMIN')")
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyCreateRequest request
    ) {

        Company company =
                CompanyMapper.toEntity(request);

        Company createdCompany =
                companyService.createCompany(company);

        CompanyResponse response =
                CompanyMapper.toResponse(createdCompany);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id
    ) {

        Company company =
                companyService.getCompanyById(id);

        return ResponseEntity.ok(
                CompanyMapper.toResponse(company)
        );
    }

    @Override
    @GetMapping("/domain")
    public ResponseEntity<CompanyResponse> getCompanyByDomain(
            @RequestParam String domain
    ) {

        Company company =
                companyService.getCompanyByDomain(domain);

        return ResponseEntity.ok(
                CompanyMapper.toResponse(company)
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CompanyResponse>>
    getAllCompanies() {

        List<CompanyResponse> responses =
                companyService.getAllCompanies()
                        .stream()
                        .map(CompanyMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("@access.canAdminCompany(#id)")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id,
                                                          @Valid @RequestBody CompanyUpdateRequest request) {
        Company company = companyService.getCompanyById(id);
        CompanyMapper.applyUpdate(company, request);
        return ResponseEntity.ok(CompanyMapper.toResponse(companyService.updateCompany(id, company)));
    }
}

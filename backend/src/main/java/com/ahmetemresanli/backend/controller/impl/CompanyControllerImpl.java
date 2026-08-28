package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICompanyController;
import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.service.ICompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyControllerImpl implements ICompanyController {

    private final ICompanyService companyService;

    public CompanyControllerImpl(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {

        Company savedCompany = companyService.createCompany(company);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {

        Company company = companyService.getCompanyById(id);

        return ResponseEntity.ok(company);
    }

    @Override
    @GetMapping("/domain/{domain}")
    public ResponseEntity<Company> getCompanyByDomain(@PathVariable String domain) {
        Company company = companyService.getCompanyByDomain(domain);

        return ResponseEntity.ok(company);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();

        return ResponseEntity.ok(companies);
    }
}

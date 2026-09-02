package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.CompanyCreateRequest;
import com.ahmetemresanli.backend.dto.response.CompanyResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompanyController {

    ResponseEntity<CompanyResponse> createCompany(
            CompanyCreateRequest request
    );

    ResponseEntity<CompanyResponse> getCompanyById(
            Long id
    );

    ResponseEntity<CompanyResponse> getCompanyByDomain(
            String domain
    );

    ResponseEntity<List<CompanyResponse>> getAllCompanies();
}
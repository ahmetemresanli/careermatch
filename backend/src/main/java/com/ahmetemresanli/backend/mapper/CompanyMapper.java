package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.CompanyCreateRequest;
import com.ahmetemresanli.backend.dto.response.CompanyResponse;
import com.ahmetemresanli.backend.entity.Company;

public final class CompanyMapper {

    private CompanyMapper() {
    }

    public static Company toEntity(
            CompanyCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        Company company = new Company();

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setIndustry(request.getIndustry());
        company.setCity(request.getCity());
        company.setCountry(request.getCountry());
        company.setWebsiteUrl(request.getWebsiteUrl());
        company.setLogoUrl(request.getLogoUrl());
        company.setDomain(request.getDomain());
        company.setEmployeeCount(request.getEmployeeCount());

        return company;
    }

    public static CompanyResponse toResponse(
            Company company
    ) {

        if (company == null) {
            return null;
        }

        CompanyResponse response = new CompanyResponse();

        response.setId(company.getId());
        response.setName(company.getName());
        response.setDescription(company.getDescription());
        response.setIndustry(company.getIndustry());
        response.setCity(company.getCity());
        response.setCountry(company.getCountry());
        response.setWebsiteUrl(company.getWebsiteUrl());
        response.setLogoUrl(company.getLogoUrl());
        response.setDomain(company.getDomain());
        response.setEmployeeCount(company.getEmployeeCount());
        response.setVerified(company.isVerified());
        response.setActive(company.isActive());
        response.setCreatedAt(company.getCreatedAt());
        response.setUpdatedAt(company.getUpdatedAt());

        return response;
    }
}
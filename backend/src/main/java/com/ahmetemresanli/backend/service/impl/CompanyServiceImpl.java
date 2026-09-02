package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.service.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(
            CompanyRepository companyRepository
    ) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {

        if (company.getName() == null
                || company.getName().isBlank()) {

            throw new BusinessException(
                    "Company name cannot be empty"
            );
        }

        if (company.getDomain() == null
                || company.getDomain().isBlank()) {

            throw new BusinessException(
                    "Company domain cannot be empty"
            );
        }

        if (company.getEmployeeCount() != null
                && company.getEmployeeCount() < 0) {

            throw new BusinessException(
                    "Employee count cannot be negative"
            );
        }

        String companyName =
                company.getName().trim();

        String domain =
                company.getDomain()
                        .trim()
                        .toLowerCase();

        if (companyRepository.existsByDomain(domain)) {

            throw new DuplicateResourceException(
                    "Company domain already exists"
            );
        }

        company.setName(companyName);
        company.setDomain(domain);

        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {

        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found"
                        )
                );
    }

    @Override
    public Company getCompanyByDomain(String domain) {

        if (domain == null || domain.isBlank()) {
            throw new BusinessException(
                    "Company domain cannot be empty"
            );
        }

        String normalizedDomain =
                domain.trim().toLowerCase();

        return companyRepository
                .findByDomain(normalizedDomain)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found"
                        )
                );
    }

    @Override
    public List<Company> getAllCompanies() {

        return companyRepository.findAll();
    }
}
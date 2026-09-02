package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.service.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {

        if (company.getDomain() != null
                && !company.getDomain().isBlank()
                && companyRepository.existsByDomain(company.getDomain())) {

            throw new DuplicateResourceException(
                    "Company domain already exists"
            );
        }

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

        return companyRepository.findByDomain(domain)
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
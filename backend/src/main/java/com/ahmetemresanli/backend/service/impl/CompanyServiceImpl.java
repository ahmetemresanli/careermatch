package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.repository.CompanyMemberRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.entity.CompanyMember;
import com.ahmetemresanli.backend.enums.CompanyMemberRole;
import com.ahmetemresanli.backend.enums.UserRole;
import com.ahmetemresanli.backend.security.AccessControlService;
import org.springframework.transaction.annotation.Transactional;
import com.ahmetemresanli.backend.service.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final UserRepository userRepository;
    private final AccessControlService access;

    public CompanyServiceImpl(
            CompanyRepository companyRepository,
            CompanyMemberRepository companyMemberRepository,
            UserRepository userRepository,
            AccessControlService access
    ) {
        this.companyRepository = companyRepository;
        this.companyMemberRepository = companyMemberRepository;
        this.userRepository = userRepository;
        this.access = access;
    }

    @Override
    @Transactional
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

        String domain = normalizeDomain(company.getDomain());

        if (companyRepository.existsByDomain(domain)) {

            throw new DuplicateResourceException(
                    "Company domain already exists"
            );
        }

        company.setName(companyName);
        company.setDomain(domain);

        Company saved = companyRepository.save(company);
        var actor = access.current();
        if (actor.role() == UserRole.COMPANY) {
            CompanyMember member = new CompanyMember();
            member.setCompany(saved);
            member.setUser(userRepository.findById(actor.id()).orElseThrow());
            member.setMemberRole(CompanyMemberRole.COMPANY_ADMIN);
            companyMemberRepository.save(member);
        }
        return saved;
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

        String normalizedDomain = normalizeDomain(domain);

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

    @Override
    public Company updateCompany(Long id, Company changes) {
        Company current = getCompanyById(id);
        String domain = normalizeDomain(changes.getDomain());
        companyRepository.findByDomain(domain).filter(other -> !other.getId().equals(id)).ifPresent(other -> {
            throw new DuplicateResourceException("Company domain already exists");
        });
        if (changes.getEmployeeCount() != null && changes.getEmployeeCount() < 0) {
            throw new BusinessException("Employee count cannot be negative");
        }
        current.setName(changes.getName()); current.setDescription(changes.getDescription()); current.setIndustry(changes.getIndustry());
        current.setCity(changes.getCity()); current.setCountry(changes.getCountry()); current.setWebsiteUrl(changes.getWebsiteUrl());
        current.setLogoUrl(changes.getLogoUrl()); current.setDomain(domain); current.setEmployeeCount(changes.getEmployeeCount());
        return companyRepository.save(current);
    }

    private String normalizeDomain(String value) {
        String domain = value.trim().toLowerCase(java.util.Locale.ROOT);
        int scheme = domain.indexOf("://");
        if (scheme >= 0) domain = domain.substring(scheme + 3);
        int slash = domain.indexOf('/');
        if (slash >= 0) domain = domain.substring(0, slash);
        int colon = domain.indexOf(':');
        if (colon >= 0) domain = domain.substring(0, colon);
        if (domain.startsWith("www.")) domain = domain.substring(4);
        while (domain.endsWith(".")) domain = domain.substring(0, domain.length() - 1);
        if (domain.isBlank() || !domain.contains(".")) throw new BusinessException("Invalid company domain");
        return domain;
    }
}

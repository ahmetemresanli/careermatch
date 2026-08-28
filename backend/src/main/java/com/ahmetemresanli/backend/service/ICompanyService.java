package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Company;

import java.util.List;

public interface ICompanyService {

    Company createCompany(Company company);

    Company getCompanyById(Long id);

    Company getCompanyByDomain(String domain);

    List<Company> getAllCompanies();
}

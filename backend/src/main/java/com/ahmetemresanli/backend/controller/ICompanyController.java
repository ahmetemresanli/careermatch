package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.Company;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompanyController {

    ResponseEntity<Company> createCompany(Company company);

    ResponseEntity<Company> getCompanyById(Long id);

    ResponseEntity<Company> getCompanyByDomain(String domain);

    ResponseEntity<List<Company>> getAllCompanies();
}

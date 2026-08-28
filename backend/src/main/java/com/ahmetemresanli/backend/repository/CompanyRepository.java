package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findByDomain(String domain);

    boolean existsByDomain(String domain);
}

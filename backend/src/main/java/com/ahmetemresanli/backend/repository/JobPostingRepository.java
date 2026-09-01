package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long>,
        JpaSpecificationExecutor<JobPosting> {

    List<JobPosting> findByCompanyId(Long companyId);
}
package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.CompanyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember,Long> {
    List<CompanyMember> findByCompanyId(Long companyId);

    List<CompanyMember> findByUserId(Long userId);

    Optional<CompanyMember> findByUserIdAndCompanyId(
            Long userId,
            Long companyId
    );

    boolean existsByUserIdAndCompanyId(
            Long userId,
            Long companyId
    );
}

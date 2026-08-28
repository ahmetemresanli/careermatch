package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.CompanyMember;
import com.ahmetemresanli.backend.enums.CompanyMemberRole;

import java.util.List;

public interface ICompanyMemberService {

    CompanyMember addMember(
            Long userId,
            Long companyId,
            CompanyMemberRole memberRole
    );

    CompanyMember getCompanyMemberById(Long id);

    List<CompanyMember> getMembersByCompanyId(Long companyId);

    List<CompanyMember> getMembershipsByUserId(Long userId);
}

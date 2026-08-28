package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.CompanyMember;
import com.ahmetemresanli.backend.enums.CompanyMemberRole;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompanyMemberController {

    ResponseEntity<CompanyMember> addMember(Long userId, Long companyId, CompanyMemberRole memberRole);

    ResponseEntity<CompanyMember> getCompanyMemberById(Long id);

    ResponseEntity<List<CompanyMember>> getMembersByCompanyId(Long companyId);

    ResponseEntity<List<CompanyMember>> getMembershipsByUserId(Long userId);
}

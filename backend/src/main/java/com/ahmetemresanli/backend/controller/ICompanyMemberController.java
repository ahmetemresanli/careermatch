package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.CompanyMemberCreateRequest;
import com.ahmetemresanli.backend.dto.response.CompanyMemberResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompanyMemberController {

    ResponseEntity<CompanyMemberResponse> addMember(
            Long userId,
            Long companyId,
            CompanyMemberCreateRequest request
    );

    ResponseEntity<CompanyMemberResponse> getCompanyMemberById(
            Long id
    );

    ResponseEntity<List<CompanyMemberResponse>> getMembersByCompanyId(
            Long companyId
    );

    ResponseEntity<List<CompanyMemberResponse>> getMembershipsByUserId(
            Long userId
    );
}
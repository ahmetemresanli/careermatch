package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.CompanyMemberCreateRequest;
import com.ahmetemresanli.backend.dto.response.CompanyMemberResponse;
import com.ahmetemresanli.backend.entity.CompanyMember;

public final class CompanyMemberMapper {

    private CompanyMemberMapper() {
    }

    public static CompanyMember toEntity(
            CompanyMemberCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        CompanyMember companyMember =
                new CompanyMember();

        companyMember.setMemberRole(
                request.getMemberRole()
        );

        return companyMember;
    }

    public static CompanyMemberResponse toResponse(
            CompanyMember companyMember
    ) {

        if (companyMember == null) {
            return null;
        }

        CompanyMemberResponse response =
                new CompanyMemberResponse();

        response.setId(companyMember.getId());

        if (companyMember.getUser() != null) {
            response.setUserId(
                    companyMember.getUser().getId()
            );
        }

        if (companyMember.getCompany() != null) {
            response.setCompanyId(
                    companyMember.getCompany().getId()
            );
        }

        response.setMemberRole(
                companyMember.getMemberRole()
        );

        response.setActive(
                companyMember.isActive()
        );

        response.setCreatedAt(
                companyMember.getCreatedAt()
        );

        response.setUpdatedAt(
                companyMember.getUpdatedAt()
        );

        return response;
    }
}
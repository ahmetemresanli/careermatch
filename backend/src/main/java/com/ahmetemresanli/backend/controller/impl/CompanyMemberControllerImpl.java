package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICompanyMemberController;
import com.ahmetemresanli.backend.dto.request.CompanyMemberCreateRequest;
import com.ahmetemresanli.backend.dto.response.CompanyMemberResponse;
import com.ahmetemresanli.backend.entity.CompanyMember;
import com.ahmetemresanli.backend.mapper.CompanyMemberMapper;
import com.ahmetemresanli.backend.service.ICompanyMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-members")
public class CompanyMemberControllerImpl
        implements ICompanyMemberController {

    private final ICompanyMemberService companyMemberService;

    public CompanyMemberControllerImpl(
            ICompanyMemberService companyMemberService
    ) {
        this.companyMemberService = companyMemberService;
    }

    @Override
    @PostMapping("/company/{companyId}/user/{userId}")
    public ResponseEntity<CompanyMemberResponse> addMember(
            @PathVariable Long userId,
            @PathVariable Long companyId,
            @Valid @RequestBody CompanyMemberCreateRequest request
    ) {

        CompanyMember companyMember =
                companyMemberService.addMember(
                        userId,
                        companyId,
                        request.getMemberRole()
                );

        CompanyMemberResponse response =
                CompanyMemberMapper.toResponse(companyMember);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CompanyMemberResponse>
    getCompanyMemberById(
            @PathVariable Long id
    ) {

        CompanyMember companyMember =
                companyMemberService
                        .getCompanyMemberById(id);

        return ResponseEntity.ok(
                CompanyMemberMapper.toResponse(
                        companyMember
                )
        );
    }

    @Override
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<CompanyMemberResponse>>
    getMembersByCompanyId(
            @PathVariable Long companyId
    ) {

        List<CompanyMemberResponse> responses =
                companyMemberService
                        .getMembersByCompanyId(companyId)
                        .stream()
                        .map(CompanyMemberMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CompanyMemberResponse>>
    getMembershipsByUserId(
            @PathVariable Long userId
    ) {

        List<CompanyMemberResponse> responses =
                companyMemberService
                        .getMembershipsByUserId(userId)
                        .stream()
                        .map(CompanyMemberMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}
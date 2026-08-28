package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICompanyMemberController;
import com.ahmetemresanli.backend.entity.CompanyMember;
import com.ahmetemresanli.backend.enums.CompanyMemberRole;
import com.ahmetemresanli.backend.service.ICompanyMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-members")
public class CompanyMemberControllerImpl implements ICompanyMemberController {

    private final ICompanyMemberService companyMemberService;

    public CompanyMemberControllerImpl(ICompanyMemberService companyMemberService) {
        this.companyMemberService = companyMemberService;
    }

    @Override
    @PostMapping("/user/{userId}/company/{companyId}")
    public ResponseEntity<CompanyMember> addMember(
            @PathVariable Long userId,
            @PathVariable Long companyId,
            @RequestParam CompanyMemberRole memberRole) {

        CompanyMember companyMember = companyMemberService.addMember(userId, companyId, memberRole);

        return ResponseEntity.status(HttpStatus.CREATED).body(companyMember);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CompanyMember> getCompanyMemberById(@PathVariable Long id) {

        CompanyMember companyMember = companyMemberService.getCompanyMemberById(id);

        return ResponseEntity.ok(companyMember);
    }

    @Override
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<CompanyMember>> getMembersByCompanyId(@PathVariable Long companyId) {

        List<CompanyMember> members = companyMemberService.getMembersByCompanyId(companyId);

        return ResponseEntity.ok(members);
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CompanyMember>> getMembershipsByUserId(@PathVariable Long userId) {

        List<CompanyMember> memberships = companyMemberService.getMembershipsByUserId(userId);

        return ResponseEntity.ok(memberships);
    }
}

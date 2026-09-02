package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.entity.CompanyMember;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.enums.CompanyMemberRole;
import com.ahmetemresanli.backend.enums.UserRole;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CompanyMemberRepository;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.ICompanyMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyMemberServiceImpl implements ICompanyMemberService {

    private final CompanyMemberRepository companyMemberRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public CompanyMemberServiceImpl(
            CompanyMemberRepository companyMemberRepository,
            UserRepository userRepository,
            CompanyRepository companyRepository
    ) {
        this.companyMemberRepository = companyMemberRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyMember addMember(
            Long userId,
            Long companyId,
            CompanyMemberRole memberRole
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        if (user.getRole() != UserRole.COMPANY) {
            throw new BusinessException(
                    "Only company users can be added to a company"
            );
        }

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found"
                        )
                );

        if (companyMemberRepository
                .existsByUserIdAndCompanyId(userId, companyId)) {

            throw new DuplicateResourceException(
                    "User is already a member of this company"
            );
        }

        CompanyMember companyMember = new CompanyMember();

        companyMember.setUser(user);
        companyMember.setCompany(company);
        companyMember.setMemberRole(memberRole);

        return companyMemberRepository.save(companyMember);
    }

    @Override
    public CompanyMember getCompanyMemberById(Long id) {

        return companyMemberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company member not found"
                        )
                );
    }

    @Override
    public List<CompanyMember> getMembersByCompanyId(Long companyId) {

        return companyMemberRepository.findByCompanyId(companyId);
    }

    @Override
    public List<CompanyMember> getMembershipsByUserId(Long userId) {

        return companyMemberRepository.findByUserId(userId);
    }
}
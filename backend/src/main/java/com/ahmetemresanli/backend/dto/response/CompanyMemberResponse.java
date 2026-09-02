package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.CompanyMemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CompanyMemberResponse {

    private Long id;

    private Long userId;

    private Long companyId;

    private CompanyMemberRole memberRole;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
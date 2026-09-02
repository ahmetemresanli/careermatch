package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.CompanyMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyMemberCreateRequest {

    @NotNull(message = "Company member role cannot be null")
    private CompanyMemberRole memberRole;
}
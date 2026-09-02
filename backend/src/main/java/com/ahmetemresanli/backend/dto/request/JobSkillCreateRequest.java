package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.SkillLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobSkillCreateRequest {

    @NotNull(message = "Required skill level cannot be null")
    private SkillLevel requiredSkillLevel;

    private Boolean required;
}
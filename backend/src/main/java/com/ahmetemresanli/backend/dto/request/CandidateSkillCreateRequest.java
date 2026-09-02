package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.SkillLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandidateSkillCreateRequest {

    @NotNull(message = "Skill level cannot be null")
    private SkillLevel skillLevel;

    @PositiveOrZero(
            message = "Years of experience cannot be negative"
    )
    private Integer yearsOfExperience;
}
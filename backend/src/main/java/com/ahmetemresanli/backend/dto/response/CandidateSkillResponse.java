package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.SkillLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CandidateSkillResponse {

    private Long id;

    private Long candidateProfileId;

    private Long skillId;

    private String skillName;

    private SkillLevel skillLevel;

    private Integer yearsOfExperience;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
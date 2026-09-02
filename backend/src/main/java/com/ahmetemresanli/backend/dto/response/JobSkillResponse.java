package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.SkillLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class JobSkillResponse {

    private Long id;

    private Long jobPostingId;

    private Long skillId;

    private String skillName;

    private SkillLevel requiredSkillLevel;

    private boolean required;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SkillCreateRequest {

    @NotBlank(message = "Skill name cannot be empty")
    @Size(
            max = 100,
            message = "Skill name cannot exceed 100 characters"
    )
    private String name;
}
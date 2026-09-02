package com.ahmetemresanli.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SkillResponse {

    private Long id;

    private String name;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
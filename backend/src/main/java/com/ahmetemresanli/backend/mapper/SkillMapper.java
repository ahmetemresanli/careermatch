package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.SkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.SkillResponse;
import com.ahmetemresanli.backend.entity.Skill;

public final class SkillMapper {

    private SkillMapper() {
    }

    public static Skill toEntity(
            SkillCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        Skill skill = new Skill();

        skill.setName(request.getName());

        return skill;
    }

    public static SkillResponse toResponse(
            Skill skill
    ) {

        if (skill == null) {
            return null;
        }

        SkillResponse response = new SkillResponse();

        response.setId(skill.getId());
        response.setName(skill.getName());
        response.setActive(skill.isActive());
        response.setCreatedAt(skill.getCreatedAt());
        response.setUpdatedAt(skill.getUpdatedAt());

        return response;
    }
}
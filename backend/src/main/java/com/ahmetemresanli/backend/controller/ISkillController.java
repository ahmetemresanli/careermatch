package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.SkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.SkillResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISkillController {

    ResponseEntity<SkillResponse> createSkill(
            SkillCreateRequest request
    );

    ResponseEntity<SkillResponse> getSkillById(
            Long id
    );

    ResponseEntity<SkillResponse> getSkillByName(
            String name
    );

    ResponseEntity<List<SkillResponse>> getAllSkills();
}
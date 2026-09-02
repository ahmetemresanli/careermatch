package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ISkillController;
import com.ahmetemresanli.backend.dto.request.SkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.SkillResponse;
import com.ahmetemresanli.backend.entity.Skill;
import com.ahmetemresanli.backend.mapper.SkillMapper;
import com.ahmetemresanli.backend.service.ISkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillControllerImpl
        implements ISkillController {

    private final ISkillService skillService;

    public SkillControllerImpl(
            ISkillService skillService
    ) {
        this.skillService = skillService;
    }

    @Override
    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(
            @Valid @RequestBody SkillCreateRequest request
    ) {

        Skill skill =
                SkillMapper.toEntity(request);

        Skill createdSkill =
                skillService.createSkill(skill);

        SkillResponse response =
                SkillMapper.toResponse(createdSkill);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(
            @PathVariable Long id
    ) {

        Skill skill =
                skillService.getSkillById(id);

        return ResponseEntity.ok(
                SkillMapper.toResponse(skill)
        );
    }

    @Override
    @GetMapping("/name")
    public ResponseEntity<SkillResponse> getSkillByName(
            @RequestParam String name
    ) {

        Skill skill =
                skillService.getSkillByName(name);

        return ResponseEntity.ok(
                SkillMapper.toResponse(skill)
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<List<SkillResponse>>
    getAllSkills() {

        List<SkillResponse> responses =
                skillService.getAllSkills()
                        .stream()
                        .map(SkillMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}
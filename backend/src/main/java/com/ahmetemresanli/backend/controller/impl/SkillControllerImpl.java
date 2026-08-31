package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ISkillController;
import com.ahmetemresanli.backend.entity.Skill;
import com.ahmetemresanli.backend.service.ISkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillControllerImpl implements ISkillController {

    private final ISkillService skillService;

    public SkillControllerImpl(ISkillService skillService) {
        this.skillService = skillService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Skill> createSkill(
            @RequestBody Skill skill
    ) {

        Skill savedSkill = skillService.createSkill(skill);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedSkill);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(
            @PathVariable Long id
    ) {

        Skill skill = skillService.getSkillById(id);

        return ResponseEntity.ok(skill);
    }

    @Override
    @GetMapping("/name/{name}")
    public ResponseEntity<Skill> getSkillByName(
            @PathVariable String name
    ) {

        Skill skill = skillService.getSkillByName(name);

        return ResponseEntity.ok(skill);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {

        List<Skill> skills = skillService.getAllSkills();

        return ResponseEntity.ok(skills);
    }
}
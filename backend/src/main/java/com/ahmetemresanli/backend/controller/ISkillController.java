package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.Skill;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISkillController {

    ResponseEntity<Skill> createSkill(Skill skill);

    ResponseEntity<Skill> getSkillById(Long id);

    ResponseEntity<Skill> getSkillByName(String name);

    ResponseEntity<List<Skill>> getAllSkills();
}
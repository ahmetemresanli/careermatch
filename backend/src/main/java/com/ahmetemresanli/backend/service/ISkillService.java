package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Skill;

import java.util.List;

public interface ISkillService {

    Skill createSkill(Skill skill);

    Skill getSkillById(Long id);

    Skill getSkillByName(String name);

    List<Skill> getAllSkills();
}
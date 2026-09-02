package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Skill;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.SkillRepository;
import com.ahmetemresanli.backend.service.ISkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements ISkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill createSkill(Skill skill) {

        if (skill.getName() == null || skill.getName().isBlank()) {
            throw new BusinessException(
                    "Skill name cannot be empty"
            );
        }

        String skillName = skill.getName().trim();

        if (skillRepository.existsByName(skillName)) {
            throw new DuplicateResourceException(
                    "Skill already exists"
            );
        }

        skill.setName(skillName);

        return skillRepository.save(skill);
    }

    @Override
    public Skill getSkillById(Long id) {

        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found"
                        )
                );
    }

    @Override
    public Skill getSkillByName(String name) {

        return skillRepository.findByName(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found"
                        )
                );
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
package com.ahmetemresanli.backend.service.matching;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.entity.Experience;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import com.ahmetemresanli.backend.repository.ExperienceRepository;
import com.ahmetemresanli.backend.repository.EmploymentVerificationRepository;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MatchScoreCalculator {

    private static final double SKILL_WEIGHT = 50.0;
    private static final double EXPERIENCE_WEIGHT = 20.0;
    private static final double WORK_MODEL_WEIGHT = 10.0;
    private static final double SALARY_WEIGHT = 10.0;
    private static final double LOCATION_WEIGHT = 10.0;

    private final ExperienceRepository experienceRepository;
    private final EmploymentVerificationRepository verificationRepository;

    public MatchScoreCalculator(ExperienceRepository experienceRepository,
                                EmploymentVerificationRepository verificationRepository) {
        this.experienceRepository = experienceRepository;
        this.verificationRepository = verificationRepository;
    }

    public BigDecimal calculateScore(
            CandidateProfile candidate,
            JobPosting jobPosting
    ) {

        double skillScore =
                calculateSkillScore(candidate, jobPosting);

        double experienceScore =
                calculateExperienceScore(candidate, jobPosting);

        double workModelScore =
                calculateWorkModelScore(candidate, jobPosting);

        double salaryScore =
                calculateSalaryScore(candidate, jobPosting);

        double locationScore =
                calculateLocationScore(candidate, jobPosting);

        double totalScore =
                skillScore
                        + experienceScore
                        + workModelScore
                        + salaryScore
                        + locationScore;

        return BigDecimal.valueOf(totalScore).setScale(2, RoundingMode.HALF_UP);
    }

    private double calculateSkillScore(
            CandidateProfile candidate,
            JobPosting jobPosting
    ) {

        if (jobPosting.getJobSkills() == null
                || jobPosting.getJobSkills().isEmpty()) {

            return 0.0;
        }

        if (candidate.getCandidateSkills() == null
                || candidate.getCandidateSkills().isEmpty()) {

            return 0.0;
        }

        Map<Long, CandidateSkill> candidateSkillMap =
                candidate.getCandidateSkills()
                        .stream()
                        .collect(Collectors.toMap(
                                candidateSkill ->
                                        candidateSkill
                                                .getSkill()
                                                .getId(),
                                candidateSkill ->
                                        candidateSkill
                        ));

        double totalWeight = 0.0;
        double earnedWeight = 0.0;

        for (JobSkill jobSkill :
                jobPosting.getJobSkills()) {

            double skillImportance =
                    jobSkill.isRequired()
                            ? 2.0
                            : 1.0;

            totalWeight += skillImportance;

            CandidateSkill candidateSkill =
                    candidateSkillMap.get(
                            jobSkill.getSkill().getId()
                    );

            if (candidateSkill == null) {
                continue;
            }

            int candidateLevel =
                    getSkillLevelValue(
                            candidateSkill.getSkillLevel()
                    );

            int requiredLevel =
                    getSkillLevelValue(
                            jobSkill.getRequiredSkillLevel()
                    );

            double levelRatio =
                    Math.min(
                            (double) candidateLevel / requiredLevel,
                            1.0
                    );

            earnedWeight +=
                    skillImportance * levelRatio;
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return (earnedWeight / totalWeight)
                * SKILL_WEIGHT;
    }

    private double calculateExperienceScore(
            CandidateProfile candidate,
            JobPosting jobPosting
    ) {

        Integer requiredYears =
                jobPosting.getMinimumExperienceYears();

        /*
         * İlanda deneyim şartı yoksa
         * aday bu kriteri tamamen karşılamış kabul edilir.
         */
        if (requiredYears == null || requiredYears <= 0) {
            return EXPERIENCE_WEIGHT;
        }

        List<Experience> experiences =
                experienceRepository
                        .findByCandidateProfileId(
                                candidate.getId()
                        );

        if (experiences.isEmpty()) {
            return 0.0;
        }

        List<Experience> verifiedExperiences = experiences.stream()
                .filter(experience -> verificationRepository
                        .findFirstByExperienceIdAndStatusOrderByCreatedAtDesc(experience.getId(), VerificationStatus.VERIFIED)
                        .isPresent())
                .toList();
        // Eski adayların puanını kökten bozmamak için hiç doğrulama yoksa mevcut davranış korunur.
        if (!verifiedExperiences.isEmpty()) {
            experiences = verifiedExperiences;
        }

        long totalMonths = 0;

        for (Experience experience : experiences) {

            LocalDate startDate =
                    experience.getStartDate();

            LocalDate endDate =
                    experience.isCurrentlyWorking()
                            ? LocalDate.now()
                            : experience.getEndDate();

            if (startDate == null || endDate == null) {
                continue;
            }

            long months =
                    ChronoUnit.MONTHS.between(
                            startDate,
                            endDate
                    );

            if (months > 0) {
                totalMonths += months;
            }
        }

        double candidateYears =
                totalMonths / 12.0;

        double experienceRatio =
                Math.min(
                        candidateYears / requiredYears,
                        1.0
                );

        return experienceRatio
                * EXPERIENCE_WEIGHT;
    }

    private double calculateWorkModelScore(
            CandidateProfile candidate,
            JobPosting jobPosting
    ) {

        if (candidate.getPreferredWorkModel() == null
                || jobPosting.getWorkModel() == null) {

            return 0.0;
        }

        if (candidate.getPreferredWorkModel()
                == jobPosting.getWorkModel()) {

            return WORK_MODEL_WEIGHT;
        }

        return 0.0;
    }

    private double calculateSalaryScore(
            CandidateProfile candidate,
            JobPosting jobPosting
    ) {

        if (candidate.getExpectedMinSalary() == null) {
            return 0.0;
        }

        BigDecimal candidateMinimum =
                candidate.getExpectedMinSalary();

        if (jobPosting.getMaximumSalary() != null) {

            if (jobPosting.getMaximumSalary()
                    .compareTo(candidateMinimum) >= 0) {

                return SALARY_WEIGHT;
            }

            return 0.0;
        }

        if (jobPosting.getMinimumSalary() != null
                && jobPosting.getMinimumSalary()
                .compareTo(candidateMinimum) >= 0) {

            return SALARY_WEIGHT;
        }

        return 0.0;
    }

    private double calculateLocationScore(
            CandidateProfile candidate,
            JobPosting jobPosting
    ) {

        if (jobPosting.getWorkModel()
                == WorkModel.REMOTE) {

            return LOCATION_WEIGHT;
        }

        String candidateCity =
                candidate.getCity();

        String jobCity =
                jobPosting.getCity();

        if (candidateCity != null
                && jobCity != null
                && candidateCity.equalsIgnoreCase(jobCity)) {

            return LOCATION_WEIGHT;
        }

        String candidateCountry =
                candidate.getCountry();

        String jobCountry =
                jobPosting.getCountry();

        if (candidateCountry != null
                && jobCountry != null
                && candidateCountry.equalsIgnoreCase(jobCountry)) {

            return LOCATION_WEIGHT / 2;
        }

        return 0.0;
    }

    private int getSkillLevelValue(
            SkillLevel skillLevel
    ) {

        return switch (skillLevel) {

            case BEGINNER -> 1;
            case INTERMEDIATE -> 2;
            case ADVANCED -> 3;
            case EXPERT -> 4;
        };
    }
}

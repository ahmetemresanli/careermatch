package com.ahmetemresanli.backend.service.matching;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MatchScoreCalculator {

    private static final double SKILL_WEIGHT = 60.0;
    private static final double WORK_MODEL_WEIGHT = 15.0;
    private static final double SALARY_WEIGHT = 15.0;
    private static final double LOCATION_WEIGHT = 10.0;

    public BigDecimal calculateScore(
            CandidateProfile candidate,
            JobPosting jobPosting
    ) {

        double skillScore =
                calculateSkillScore(candidate, jobPosting);

        double workModelScore =
                calculateWorkModelScore(candidate, jobPosting);

        double salaryScore =
                calculateSalaryScore(candidate, jobPosting);

        double locationScore =
                calculateLocationScore(candidate, jobPosting);

        double totalScore =
                skillScore
                        + workModelScore
                        + salaryScore
                        + locationScore;

        return BigDecimal.valueOf(totalScore)
                .setScale(2, RoundingMode.HALF_UP);
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

            /*
             * Required skill daha önemli.
             *
             * required = true  -> ağırlık 2
             * required = false -> ağırlık 1
             */
            double skillImportance =
                    jobSkill.isRequired()
                            ? 2.0
                            : 1.0;

            totalWeight += skillImportance;

            CandidateSkill candidateSkill =
                    candidateSkillMap.get(
                            jobSkill.getSkill().getId()
                    );

            // Adayda bu skill hiç yok
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

            /*
             * Örnek:
             *
             * Candidate INTERMEDIATE = 2
             * Job ADVANCED = 3
             *
             * 2 / 3 = %66 skill karşılanıyor.
             *
             * Candidate EXPERT = 4
             * Job ADVANCED = 3
             *
             * 4 / 3 > 1 olduğu için maksimum 1 alınır.
             */
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

        /*
         * Adayın minimum beklentisini esas alıyoruz.
         *
         * Örnek:
         * Candidate minimum = 50.000
         * Job maximum = 70.000
         *
         * Firma adayın beklentisini karşılayabiliyor.
         */
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

        /*
         * maximumSalary girilmemiş ama
         * minimumSalary aday beklentisinin üzerindeyse
         * yine uyumlu kabul ediyoruz.
         */
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

        /*
         * İlan tamamen REMOTE ise
         * şehir uyumuna gerek yok.
         */
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

        /*
         * Şehir farklı ama ülke aynıysa
         * lokasyon puanının yarısını veriyoruz.
         */
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
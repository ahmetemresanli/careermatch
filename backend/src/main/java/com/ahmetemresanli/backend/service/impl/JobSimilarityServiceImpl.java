package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.dto.response.SimilarJobResponse;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.repository.JobSkillRepository;
import com.ahmetemresanli.backend.service.IJobSimilarityService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class JobSimilarityServiceImpl implements IJobSimilarityService {
    private final JobPostingRepository jobs; private final JobSkillRepository jobSkills;
    public JobSimilarityServiceImpl(JobPostingRepository jobs, JobSkillRepository jobSkills) { this.jobs = jobs; this.jobSkills = jobSkills; }
    @Override public List<SimilarJobResponse> findSimilar(Long jobId, int limit) {
        if (limit < 1 || limit > 50) throw new BusinessException("Limit must be between 1 and 50");
        JobPosting source = jobs.findById(jobId).filter(j -> j.getStatus() == JobStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Published job posting not found"));
        Set<Long> sourceSkills = skillIds(source.getId());
        return jobs.findByStatus(JobStatus.PUBLISHED).stream().filter(job -> !job.getId().equals(jobId))
                .map(job -> map(job, score(source, job, sourceSkills, skillIds(job.getId()))))
                .sorted(Comparator.comparing(SimilarJobResponse::score).reversed()).limit(limit).toList();
    }
    private Set<Long> skillIds(Long id) {
        Set<Long> ids = new HashSet<>(); for (JobSkill skill : jobSkills.findByJobPostingId(id)) ids.add(skill.getSkill().getId()); return ids;
    }
    private double score(JobPosting a, JobPosting b, Set<Long> aSkills, Set<Long> bSkills) {
        double score = 0;
        if (!aSkills.isEmpty() || !bSkills.isEmpty()) {
            Set<Long> union = new HashSet<>(aSkills); union.addAll(bSkills);
            Set<Long> common = new HashSet<>(aSkills); common.retainAll(bSkills);
            score += 40.0 * common.size() / union.size();
        }
        if (a.getJobLevel() == b.getJobLevel()) score += 10;
        if (a.getEmploymentType() == b.getEmploymentType()) score += 10;
        if (a.getWorkModel() == b.getWorkModel()) score += 10;
        if (same(a.getCity(), b.getCity())) score += 10; else if (same(a.getCountry(), b.getCountry())) score += 5;
        score += titleSimilarity(a.getTitle(), b.getTitle()) * 20;
        return Math.min(100, score);
    }
    private double titleSimilarity(String a, String b) {
        Set<String> left = words(a), right = words(b); if (left.isEmpty() || right.isEmpty()) return 0;
        Set<String> union = new HashSet<>(left); union.addAll(right); left.retainAll(right); return (double) left.size() / union.size();
    }
    private Set<String> words(String value) { return new HashSet<>(Arrays.asList(value.toLowerCase(Locale.ROOT).split("\\W+"))); }
    private boolean same(String a, String b) { return a != null && b != null && a.equalsIgnoreCase(b); }
    private SimilarJobResponse map(JobPosting job, double score) {
        return new SimilarJobResponse(job.getId(), job.getTitle(), job.getCompany().getId(), job.getCompany().getName(),
                BigDecimal.valueOf(score).setScale(2, RoundingMode.HALF_UP));
    }
}

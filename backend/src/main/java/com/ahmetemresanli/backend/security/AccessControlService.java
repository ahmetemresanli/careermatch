package com.ahmetemresanli.backend.security;

import com.ahmetemresanli.backend.entity.*;
import com.ahmetemresanli.backend.enums.CompanyMemberRole;
import com.ahmetemresanli.backend.enums.UserRole;
import com.ahmetemresanli.backend.repository.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("access")
public class AccessControlService {

    private final CandidateProfileRepository candidateProfiles;
    private final CompanyMemberRepository companyMembers;
    private final ApplicationRepository applications;
    private final ConversationRepository conversations;
    private final MessageRepository messages;
    private final EducationRepository educations;
    private final ExperienceRepository experiences;
    private final ResumeRepository resumes;
    private final CandidateSkillRepository candidateSkills;
    private final InterviewRepository interviews;
    private final JobPostingRepository jobs;
    private final MatchRepository matches;
    private final ReferenceRequestRepository referenceRequests;
    private final ReferenceRepository references;
    private final SkillEndorsementRepository endorsements;
    private final EducationVerificationRepository educationVerifications;
    private final EmploymentVerificationRepository employmentVerifications;

    public AccessControlService(CandidateProfileRepository candidateProfiles, CompanyMemberRepository companyMembers,
                                ApplicationRepository applications, ConversationRepository conversations,
                                MessageRepository messages, EducationRepository educations,
                                ExperienceRepository experiences, ResumeRepository resumes,
                                CandidateSkillRepository candidateSkills, InterviewRepository interviews,
                                JobPostingRepository jobs, MatchRepository matches,
                                ReferenceRequestRepository referenceRequests, ReferenceRepository references,
                                SkillEndorsementRepository endorsements,
                                EducationVerificationRepository educationVerifications,
                                EmploymentVerificationRepository employmentVerifications) {
        this.candidateProfiles = candidateProfiles;
        this.companyMembers = companyMembers;
        this.applications = applications;
        this.conversations = conversations;
        this.messages = messages;
        this.educations = educations;
        this.experiences = experiences;
        this.resumes = resumes;
        this.candidateSkills = candidateSkills;
        this.interviews = interviews;
        this.jobs = jobs;
        this.matches = matches;
        this.referenceRequests = referenceRequests;
        this.references = references;
        this.endorsements = endorsements;
        this.educationVerifications = educationVerifications;
        this.employmentVerifications = employmentVerifications;
    }

    public AuthenticatedUser current() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthenticatedUser user)) {
            throw new AccessDeniedException("Authentication is required");
        }
        return user;
    }

    public Long currentUserId() { return current().id(); }
    public boolean isAdmin() { return current().role() == UserRole.ADMIN; }
    public boolean isSelf(Long userId) { return isAdmin() || current().id().equals(userId); }

    public boolean ownsCandidate(Long candidateId) {
        return isAdmin() || candidateProfiles.findById(candidateId)
                .map(p -> p.getUser().getId().equals(current().id())).orElse(false);
    }

    public boolean ownsEducation(Long educationId) {
        return isAdmin() || educations.findById(educationId).map(e -> ownsCandidate(e.getCandidateProfile().getId())).orElse(false);
    }

    public boolean ownsExperience(Long experienceId) {
        return isAdmin() || experiences.findById(experienceId).map(e -> ownsCandidate(e.getCandidateProfile().getId())).orElse(false);
    }

    public boolean ownsResume(Long resumeId) {
        return isAdmin() || resumes.findById(resumeId).map(r -> ownsCandidate(r.getCandidateProfile().getId())).orElse(false);
    }

    public boolean ownsCandidateSkill(Long candidateSkillId) {
        return isAdmin() || candidateSkills.findById(candidateSkillId)
                .map(s -> ownsCandidate(s.getCandidateProfile().getId())).orElse(false);
    }

    public boolean isCompanyMember(Long companyId) {
        return isAdmin() || companyMembers.findByUserIdAndCompanyId(current().id(), companyId)
                .map(CompanyMember::isActive).orElse(false);
    }

    public boolean canAdminCompany(Long companyId) {
        return isAdmin() || companyMembers.findByUserIdAndCompanyId(current().id(), companyId)
                .filter(CompanyMember::isActive)
                .map(m -> m.getMemberRole() == CompanyMemberRole.COMPANY_ADMIN).orElse(false);
    }

    public boolean managesJob(Long jobId) {
        return isAdmin() || jobs.findById(jobId).map(j -> isCompanyMember(j.getCompany().getId())).orElse(false);
    }

    public boolean canAccessApplication(Long applicationId) {
        return isAdmin() || applications.findById(applicationId).map(a ->
                ownsCandidate(a.getCandidateProfile().getId()) || isCompanyMember(a.getJobPosting().getCompany().getId())).orElse(false);
    }

    public boolean managesApplication(Long applicationId) {
        return isAdmin() || applications.findById(applicationId)
                .map(a -> isCompanyMember(a.getJobPosting().getCompany().getId())).orElse(false);
    }

    public boolean canAccessConversation(Long conversationId) {
        return isAdmin() || conversations.findById(conversationId).map(c ->
                ownsCandidate(c.getCandidateProfile().getId()) || isCompanyMember(c.getCompany().getId())).orElse(false);
    }

    public boolean canAccessMessage(Long messageId) {
        return isAdmin() || messages.findById(messageId).map(m -> canAccessConversation(m.getConversation().getId())).orElse(false);
    }

    public boolean canAccessInterview(Long interviewId) {
        return isAdmin() || interviews.findById(interviewId).map(i -> canAccessApplication(i.getApplication().getId())).orElse(false);
    }

    public boolean canAccessMatch(Long id) {
        return isAdmin() || matches.findById(id).map(m -> ownsCandidate(m.getCandidateProfile().getId())
                || managesJob(m.getJobPosting().getId())).orElse(false);
    }

    public boolean ownsReferenceRequest(Long id) {
        return isAdmin() || referenceRequests.findById(id).map(r -> ownsCandidate(r.getCandidateProfile().getId())).orElse(false);
    }

    public boolean ownsReference(Long id) {
        return isAdmin() || references.findById(id).map(r -> ownsCandidate(r.getCandidateProfile().getId())).orElse(false);
    }

    public boolean ownsEndorsement(Long id) {
        return isAdmin() || endorsements.findById(id).map(e -> ownsCandidateSkill(e.getCandidateSkill().getId())).orElse(false);
    }

    public boolean canAccessEducationVerification(Long id) {
        return isAdmin() || educationVerifications.findById(id).map(v -> ownsEducation(v.getEducation().getId())).orElse(false);
    }

    public boolean canAccessEmploymentVerification(Long id) {
        return isAdmin() || employmentVerifications.findById(id).map(v -> ownsExperience(v.getExperience().getId())).orElse(false);
    }
}

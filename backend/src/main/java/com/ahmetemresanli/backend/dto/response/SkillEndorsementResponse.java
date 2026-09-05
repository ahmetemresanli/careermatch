package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.ReferenceRelation;
import com.ahmetemresanli.backend.enums.SkillEndorsementStatus;

import java.time.LocalDateTime;

public class SkillEndorsementResponse {

    private Long id;

    private Long candidateSkillId;
    private Long candidateProfileId;

    private Long skillId;
    private String skillName;

    private String endorserName;
    private String endorserEmail;

    private ReferenceRelation relation;

    private String requestMessage;
    private String endorsementComment;

    private SkillEndorsementStatus status;

    /*
     * Geçici olarak Postman testi için dönüyor.
     * Spring Mail geldiğinde kaldıracağız.
     */
    private String token;

    private LocalDateTime expiresAt;
    private LocalDateTime respondedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SkillEndorsementResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidateSkillId() {
        return candidateSkillId;
    }

    public void setCandidateSkillId(Long candidateSkillId) {
        this.candidateSkillId = candidateSkillId;
    }

    public Long getCandidateProfileId() {
        return candidateProfileId;
    }

    public void setCandidateProfileId(Long candidateProfileId) {
        this.candidateProfileId = candidateProfileId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getEndorserName() {
        return endorserName;
    }

    public void setEndorserName(String endorserName) {
        this.endorserName = endorserName;
    }

    public String getEndorserEmail() {
        return endorserEmail;
    }

    public void setEndorserEmail(String endorserEmail) {
        this.endorserEmail = endorserEmail;
    }

    public ReferenceRelation getRelation() {
        return relation;
    }

    public void setRelation(ReferenceRelation relation) {
        this.relation = relation;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getEndorsementComment() {
        return endorsementComment;
    }

    public void setEndorsementComment(String endorsementComment) {
        this.endorsementComment = endorsementComment;
    }

    public SkillEndorsementStatus getStatus() {
        return status;
    }

    public void setStatus(SkillEndorsementStatus status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
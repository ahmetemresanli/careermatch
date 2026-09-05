package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.ReferenceRelation;
import com.ahmetemresanli.backend.enums.SkillEndorsementStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "skill_endorsements",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "candidate_skill_id",
                                "endorser_email"
                        }
                )
        }
)
public class SkillEndorsement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Hangi adayın hangi skill'i onaylanıyor?
     *
     * Örnek:
     * Mehmet -> Java -> ADVANCED
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "candidate_skill_id",
            nullable = false
    )
    private CandidateSkill candidateSkill;

    @Column(
            name = "endorser_name",
            nullable = false,
            length = 200
    )
    private String endorserName;

    @Column(
            name = "endorser_email",
            nullable = false,
            length = 255
    )
    private String endorserEmail;

    /*
     * Endorsement veren kişinin adayla ilişkisi.
     *
     * MANAGER
     * COLLEAGUE
     * CLIENT
     * ACADEMIC
     * OTHER
     *
     * ReferenceRelation enum'unu burada
     * tekrar kullanabiliriz.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReferenceRelation relation;

    /*
     * Aday endorsement isterken karşı tarafa
     * bir mesaj gönderebilir.
     */
    @Column(length = 1000)
    private String requestMessage;

    /*
     * Endorsement veren kişi isterse
     * skill hakkında kısa bir yorum yazabilir.
     */
    @Column(length = 2000)
    private String endorsementComment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SkillEndorsementStatus status =
            SkillEndorsementStatus.PENDING;

    /*
     * Mail ile gönderilecek doğrulama token'ı.
     *
     * Spring Mail'i daha sonra bağlayacağız.
     * Şimdilik Postman response'ta kullanacağız.
     */
    @Column(
            nullable = false,
            unique = true,
            length = 255
    )
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime respondedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public CandidateSkill getCandidateSkill() {
        return candidateSkill;
    }

    public void setCandidateSkill(
            CandidateSkill candidateSkill
    ) {
        this.candidateSkill = candidateSkill;
    }

    public String getEndorserName() {
        return endorserName;
    }

    public void setEndorserName(
            String endorserName
    ) {
        this.endorserName = endorserName;
    }

    public String getEndorserEmail() {
        return endorserEmail;
    }

    public void setEndorserEmail(
            String endorserEmail
    ) {
        this.endorserEmail = endorserEmail;
    }

    public ReferenceRelation getRelation() {
        return relation;
    }

    public void setRelation(
            ReferenceRelation relation
    ) {
        this.relation = relation;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(
            String requestMessage
    ) {
        this.requestMessage = requestMessage;
    }

    public String getEndorsementComment() {
        return endorsementComment;
    }

    public void setEndorsementComment(
            String endorsementComment
    ) {
        this.endorsementComment = endorsementComment;
    }

    public SkillEndorsementStatus getStatus() {
        return status;
    }

    public void setStatus(
            SkillEndorsementStatus status
    ) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(
            String token
    ) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(
            LocalDateTime expiresAt
    ) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(
            LocalDateTime respondedAt
    ) {
        this.respondedAt = respondedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
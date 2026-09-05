package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.InterviewMode;
import com.ahmetemresanli.backend.enums.InterviewStatus;
import com.ahmetemresanli.backend.enums.InterviewType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Bir Application için birden fazla
     * interview olabilir.
     *
     * Örn:
     * HR
     * Technical
     * Final
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "application_id",
            nullable = false
    )
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InterviewType interviewType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InterviewMode interviewMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InterviewStatus status =
            InterviewStatus.SCHEDULED;

    /*
     * Görüşmenin yapılacağı tarih ve saat.
     */
    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    /*
     * Dakika cinsinden.
     *
     * Örn: 60
     */
    @Column(nullable = false)
    private Integer durationMinutes;

    /*
     * ONLINE görüşmeler için:
     *
     * Google Meet
     * Teams
     * Zoom
     * vb.
     */
    @Column(length = 1000)
    private String meetingUrl;

    /*
     * ONSITE görüşmeler için adres.
     */
    @Column(length = 1000)
    private String location;

    /*
     * Şirketin görüşme öncesi notu.
     */
    @Column(length = 2000)
    private String notes;

    /*
     * Görüşme tamamlandıktan sonra
     * şirket tarafından girilebilir.
     */
    @Column(length = 3000)
    private String feedback;

    @Column
    private LocalDateTime completedAt;

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

    public Application getApplication() {
        return application;
    }

    public void setApplication(
            Application application
    ) {
        this.application = application;
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(
            InterviewType interviewType
    ) {
        this.interviewType = interviewType;
    }

    public InterviewMode getInterviewMode() {
        return interviewMode;
    }

    public void setInterviewMode(
            InterviewMode interviewMode
    ) {
        this.interviewMode = interviewMode;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(
            InterviewStatus status
    ) {
        this.status = status;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(
            LocalDateTime scheduledAt
    ) {
        this.scheduledAt = scheduledAt;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(
            Integer durationMinutes
    ) {
        this.durationMinutes = durationMinutes;
    }

    public String getMeetingUrl() {
        return meetingUrl;
    }

    public void setMeetingUrl(
            String meetingUrl
    ) {
        this.meetingUrl = meetingUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(
            String location
    ) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(
            String notes
    ) {
        this.notes = notes;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(
            String feedback
    ) {
        this.feedback = feedback;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(
            LocalDateTime completedAt
    ) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
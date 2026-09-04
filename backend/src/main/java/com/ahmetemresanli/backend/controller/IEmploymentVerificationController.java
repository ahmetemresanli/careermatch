package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.EmploymentDocumentVerificationRequest;
import com.ahmetemresanli.backend.dto.request.EmploymentEmailVerificationRequest;
import com.ahmetemresanli.backend.dto.request.VerificationRejectRequest;
import com.ahmetemresanli.backend.dto.response.EmploymentVerificationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEmploymentVerificationController {

    ResponseEntity<EmploymentVerificationResponse>
    requestEmailVerification(
            Long experienceId,
            EmploymentEmailVerificationRequest request
    );

    ResponseEntity<EmploymentVerificationResponse>
    requestDocumentVerification(
            Long experienceId,
            EmploymentDocumentVerificationRequest request
    );

    ResponseEntity<EmploymentVerificationResponse>
    verifyEmail(
            String token
    );

    ResponseEntity<EmploymentVerificationResponse>
    approveDocument(
            Long verificationId
    );

    ResponseEntity<EmploymentVerificationResponse>
    rejectDocument(
            Long verificationId,
            VerificationRejectRequest request
    );

    ResponseEntity<EmploymentVerificationResponse>
    getVerificationById(
            Long id
    );

    ResponseEntity<List<EmploymentVerificationResponse>>
    getVerificationsByExperience(
            Long experienceId
    );
}
package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.EducationDocumentVerificationRequest;
import com.ahmetemresanli.backend.dto.request.EducationEmailVerificationRequest;
import com.ahmetemresanli.backend.dto.request.VerificationRejectRequest;
import com.ahmetemresanli.backend.dto.response.EducationVerificationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEducationVerificationController {

    ResponseEntity<EducationVerificationResponse>
    requestEmailVerification(
            Long educationId,
            EducationEmailVerificationRequest request
    );

    ResponseEntity<EducationVerificationResponse>
    requestDocumentVerification(
            Long educationId,
            EducationDocumentVerificationRequest request
    );

    ResponseEntity<EducationVerificationResponse>
    verifyEmail(
            String token
    );

    ResponseEntity<EducationVerificationResponse>
    approveDocument(
            Long verificationId
    );

    ResponseEntity<EducationVerificationResponse>
    rejectDocument(
            Long verificationId,
            VerificationRejectRequest request
    );

    ResponseEntity<EducationVerificationResponse>
    getVerificationById(
            Long id
    );

    ResponseEntity<List<EducationVerificationResponse>>
    getVerificationsByEducation(
            Long educationId
    );
}
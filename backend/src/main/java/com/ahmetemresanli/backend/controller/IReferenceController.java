package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.ReferenceAcceptRequest;
import com.ahmetemresanli.backend.dto.request.ReferenceRequestCreateRequest;
import com.ahmetemresanli.backend.dto.response.ReferenceRequestResponse;
import com.ahmetemresanli.backend.dto.response.ReferenceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReferenceController {

    ResponseEntity<ReferenceRequestResponse> createReferenceRequest(
            Long candidateProfileId,
            ReferenceRequestCreateRequest request
    );

    ResponseEntity<ReferenceResponse> acceptReferenceRequest(
            String token,
            ReferenceAcceptRequest request
    );

    ResponseEntity<ReferenceRequestResponse> rejectReferenceRequest(
            String token
    );

    ResponseEntity<ReferenceRequestResponse> getReferenceRequestById(
            Long id
    );

    ResponseEntity<List<ReferenceRequestResponse>>
    getReferenceRequestsByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<ReferenceResponse> getReferenceById(
            Long id
    );

    ResponseEntity<List<ReferenceResponse>> getReferencesByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<List<ReferenceResponse>> getVisibleReferencesByCandidate(
            Long candidateProfileId
    );
}
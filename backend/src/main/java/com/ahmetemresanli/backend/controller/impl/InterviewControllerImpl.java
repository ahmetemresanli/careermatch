package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IInterviewController;
import com.ahmetemresanli.backend.dto.request.InterviewCompleteRequest;
import com.ahmetemresanli.backend.dto.request.InterviewCreateRequest;
import com.ahmetemresanli.backend.dto.response.InterviewResponse;
import com.ahmetemresanli.backend.entity.Interview;
import com.ahmetemresanli.backend.mapper.InterviewMapper;
import com.ahmetemresanli.backend.service.IInterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewControllerImpl
        implements IInterviewController {

    private final IInterviewService interviewService;

    public InterviewControllerImpl(
            IInterviewService interviewService
    ) {
        this.interviewService = interviewService;
    }

    @Override
    @PostMapping("/application/{applicationId}")
    public ResponseEntity<InterviewResponse>
    createInterview(
            @PathVariable Long applicationId,
            @Valid @RequestBody
            InterviewCreateRequest request
    ) {

        Interview interview =
                interviewService.createInterview(
                        applicationId,
                        request.getInterviewType(),
                        request.getInterviewMode(),
                        request.getScheduledAt(),
                        request.getDurationMinutes(),
                        request.getMeetingUrl(),
                        request.getLocation(),
                        request.getNotes()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        InterviewMapper.toResponse(
                                interview
                        )
                );
    }

    @Override
    @PutMapping("/{interviewId}/complete")
    public ResponseEntity<InterviewResponse>
    completeInterview(
            @PathVariable Long interviewId,
            @Valid @RequestBody
            InterviewCompleteRequest request
    ) {

        Interview interview =
                interviewService.completeInterview(
                        interviewId,
                        request.getFeedback()
                );

        return ResponseEntity.ok(
                InterviewMapper.toResponse(
                        interview
                )
        );
    }

    @Override
    @PutMapping("/{interviewId}/cancel")
    public ResponseEntity<InterviewResponse>
    cancelInterview(
            @PathVariable Long interviewId
    ) {

        Interview interview =
                interviewService.cancelInterview(
                        interviewId
                );

        return ResponseEntity.ok(
                InterviewMapper.toResponse(
                        interview
                )
        );
    }

    @Override
    @GetMapping("/{interviewId}")
    public ResponseEntity<InterviewResponse>
    getInterviewById(
            @PathVariable Long interviewId
    ) {

        Interview interview =
                interviewService.getInterviewById(
                        interviewId
                );

        return ResponseEntity.ok(
                InterviewMapper.toResponse(
                        interview
                )
        );
    }

    @Override
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<InterviewResponse>>
    getInterviewsByApplication(
            @PathVariable Long applicationId
    ) {

        List<InterviewResponse> responses =
                interviewService
                        .getInterviewsByApplication(
                                applicationId
                        )
                        .stream()
                        .map(
                                InterviewMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<InterviewResponse>>
    getInterviewsByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<InterviewResponse> responses =
                interviewService
                        .getInterviewsByCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(
                                InterviewMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<InterviewResponse>>
    getInterviewsByCompany(
            @PathVariable Long companyId
    ) {

        List<InterviewResponse> responses =
                interviewService
                        .getInterviewsByCompany(
                                companyId
                        )
                        .stream()
                        .map(
                                InterviewMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }
}
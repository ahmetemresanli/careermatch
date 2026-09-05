package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ISecurityQuestionController;
import com.ahmetemresanli.backend.dto.request.SecurityAnswerRequest;
import com.ahmetemresanli.backend.dto.request.SecurityQuestionRequest;
import com.ahmetemresanli.backend.dto.response.SecurityQuestionResponse;
import com.ahmetemresanli.backend.security.AccessControlService;
import com.ahmetemresanli.backend.service.ISecurityQuestionService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/security-questions")
public class SecurityQuestionControllerImpl implements ISecurityQuestionController {
    private final ISecurityQuestionService service; private final AccessControlService access;
    public SecurityQuestionControllerImpl(ISecurityQuestionService service, AccessControlService access) {
        this.service = service; this.access = access;
    }
    @Override @PostMapping @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SecurityQuestionResponse> create(@Valid @RequestBody SecurityQuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request.question()));
    }
    @Override @GetMapping
    public ResponseEntity<List<SecurityQuestionResponse>> list() {
        return ResponseEntity.ok(service.listForUser(access.currentUserId()));
    }
    @Override @PutMapping("/{questionId}/answer")
    public ResponseEntity<SecurityQuestionResponse> saveAnswer(@PathVariable Long questionId,
                                                                @Valid @RequestBody SecurityAnswerRequest request) {
        return ResponseEntity.ok(service.saveAnswer(access.currentUserId(), questionId, request.answer()));
    }
}

package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.SecurityAnswerRequest;
import com.ahmetemresanli.backend.dto.request.SecurityQuestionRequest;
import com.ahmetemresanli.backend.dto.response.SecurityQuestionResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ISecurityQuestionController {
    ResponseEntity<SecurityQuestionResponse> create(SecurityQuestionRequest request);
    ResponseEntity<List<SecurityQuestionResponse>> list();
    ResponseEntity<SecurityQuestionResponse> saveAnswer(Long questionId, SecurityAnswerRequest request);
}

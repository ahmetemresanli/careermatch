package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.dto.response.SecurityQuestionResponse;
import java.util.List;

public interface ISecurityQuestionService {
    SecurityQuestionResponse create(String question);
    List<SecurityQuestionResponse> listForUser(Long userId);
    SecurityQuestionResponse saveAnswer(Long userId, Long questionId, String answer);
}

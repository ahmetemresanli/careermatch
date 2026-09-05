package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.dto.response.SecurityQuestionResponse;
import com.ahmetemresanli.backend.entity.SecurityQuestion;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.entity.UserSecurityAnswer;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.SecurityQuestionRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.repository.UserSecurityAnswerRepository;
import com.ahmetemresanli.backend.service.ISecurityQuestionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SecurityQuestionServiceImpl implements ISecurityQuestionService {
    private final SecurityQuestionRepository questions;
    private final UserSecurityAnswerRepository answers;
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public SecurityQuestionServiceImpl(SecurityQuestionRepository questions, UserSecurityAnswerRepository answers,
                                       UserRepository users, PasswordEncoder encoder) {
        this.questions = questions; this.answers = answers; this.users = users; this.encoder = encoder;
    }
    @Override public SecurityQuestionResponse create(String text) {
        SecurityQuestion question = new SecurityQuestion(); question.setQuestion(text.trim());
        return map(questions.save(question), false);
    }
    @Override public List<SecurityQuestionResponse> listForUser(Long userId) {
        if (!users.existsById(userId)) throw new ResourceNotFoundException("User not found");
        return questions.findByActiveTrueOrderByIdAsc().stream()
                .map(q -> map(q, answers.findByUserIdAndQuestionId(userId, q.getId()).isPresent())).toList();
    }
    @Override @Transactional public SecurityQuestionResponse saveAnswer(Long userId, Long questionId, String raw) {
        User user = users.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        SecurityQuestion q = questions.findById(questionId).filter(SecurityQuestion::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Security question not found"));
        UserSecurityAnswer answer = answers.findByUserIdAndQuestionId(userId, questionId).orElseGet(UserSecurityAnswer::new);
        answer.setUser(user); answer.setQuestion(q); answer.setAnswerHash(encoder.encode(normalize(raw)));
        answers.save(answer); return map(q, true);
    }
    private String normalize(String value) { return value.trim().toLowerCase(java.util.Locale.ROOT); }
    private SecurityQuestionResponse map(SecurityQuestion q, boolean answered) {
        return new SecurityQuestionResponse(q.getId(), q.getQuestion(), answered);
    }
}

package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.dto.response.OperationResponse;

public interface IAccountRecoveryService {
    OperationResponse forgotPassword(String email);
    OperationResponse resetPassword(String token, String newPassword);
    OperationResponse issueEmailVerification(Long userId);
    OperationResponse issueRecoveryEmailVerification(Long userId);
    OperationResponse verifyEmail(String token);
}

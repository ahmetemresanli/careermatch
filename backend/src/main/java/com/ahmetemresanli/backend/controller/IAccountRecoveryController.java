package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.ForgotPasswordRequest;
import com.ahmetemresanli.backend.dto.request.ResetPasswordRequest;
import com.ahmetemresanli.backend.dto.response.OperationResponse;
import org.springframework.http.ResponseEntity;

public interface IAccountRecoveryController {
    ResponseEntity<OperationResponse> forgotPassword(ForgotPasswordRequest request);
    ResponseEntity<OperationResponse> resetPassword(ResetPasswordRequest request);
}

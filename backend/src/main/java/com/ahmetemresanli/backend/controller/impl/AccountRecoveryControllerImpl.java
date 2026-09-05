package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IAccountRecoveryController;
import com.ahmetemresanli.backend.dto.request.ForgotPasswordRequest;
import com.ahmetemresanli.backend.dto.request.ResetPasswordRequest;
import com.ahmetemresanli.backend.dto.response.OperationResponse;
import com.ahmetemresanli.backend.service.IAccountRecoveryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account-recovery")
public class AccountRecoveryControllerImpl implements IAccountRecoveryController {
    private final IAccountRecoveryService service;
    public AccountRecoveryControllerImpl(IAccountRecoveryService service) { this.service = service; }

    @Override @PostMapping("/forgot-password")
    public ResponseEntity<OperationResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(service.forgotPassword(request.email()));
    }

    @Override @PostMapping("/reset-password")
    public ResponseEntity<OperationResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(service.resetPassword(request.token(), request.newPassword()));
    }
}

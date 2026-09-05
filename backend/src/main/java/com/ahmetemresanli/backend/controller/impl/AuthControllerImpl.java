package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IAuthController;
import com.ahmetemresanli.backend.dto.request.LoginRequest;
import com.ahmetemresanli.backend.dto.request.RegisterRequest;
import com.ahmetemresanli.backend.dto.response.AuthResponse;
import com.ahmetemresanli.backend.dto.response.OperationResponse;
import com.ahmetemresanli.backend.service.IAuthService;
import com.ahmetemresanli.backend.service.IAccountRecoveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements IAuthController {
    private final IAuthService authService;
    private final IAccountRecoveryService accountRecoveryService;

    public AuthControllerImpl(IAuthService authService, IAccountRecoveryService accountRecoveryService) {
        this.authService = authService;
        this.accountRecoveryService = accountRecoveryService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Override
    @GetMapping("/verify-email")
    public ResponseEntity<OperationResponse> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok(accountRecoveryService.verifyEmail(token));
    }
}

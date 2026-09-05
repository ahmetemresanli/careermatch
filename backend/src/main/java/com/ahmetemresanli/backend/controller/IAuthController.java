package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.LoginRequest;
import com.ahmetemresanli.backend.dto.request.RegisterRequest;
import com.ahmetemresanli.backend.dto.response.AuthResponse;
import com.ahmetemresanli.backend.dto.response.OperationResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    ResponseEntity<AuthResponse> register(RegisterRequest request);
    ResponseEntity<AuthResponse> login(LoginRequest request);
    ResponseEntity<OperationResponse> verifyEmail(String token);
}

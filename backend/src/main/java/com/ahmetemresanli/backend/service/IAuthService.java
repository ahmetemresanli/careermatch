package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.dto.request.LoginRequest;
import com.ahmetemresanli.backend.dto.request.RegisterRequest;
import com.ahmetemresanli.backend.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

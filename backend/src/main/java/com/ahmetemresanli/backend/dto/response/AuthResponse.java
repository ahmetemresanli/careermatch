package com.ahmetemresanli.backend.dto.response;

public record AuthResponse(String accessToken, String tokenType, long expiresInSeconds, UserResponse user) {
}

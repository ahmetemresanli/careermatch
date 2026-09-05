package com.ahmetemresanli.backend.security;

import com.ahmetemresanli.backend.enums.UserRole;

public record AuthenticatedUser(Long id, String email, UserRole role) {
}

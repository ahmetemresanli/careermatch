package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String email;

    private String recoveryEmail;

    private boolean emailVerified;

    private boolean recoveryEmailVerified;

    private UserRole role;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
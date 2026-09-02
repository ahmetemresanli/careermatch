package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.UserCreateRequest;
import com.ahmetemresanli.backend.dto.response.UserResponse;
import com.ahmetemresanli.backend.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(
            UserCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        User user = new User();

        user.setEmail(request.getEmail());

        /*
         * GEÇİCİ:
         * Security aşamasında BCrypt ile hash'lenecek.
         */
        user.setPasswordHash(request.getPassword());

        user.setRecoveryEmail(
                request.getRecoveryEmail()
        );

        user.setRole(request.getRole());

        return user;
    }

    public static UserResponse toResponse(
            User user
    ) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRecoveryEmail(
                user.getRecoveryEmail()
        );
        response.setEmailVerified(
                user.isEmailVerified()
        );
        response.setRecoveryEmailVerified(
                user.isRecoveryEmailVerified()
        );
        response.setRole(user.getRole());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}
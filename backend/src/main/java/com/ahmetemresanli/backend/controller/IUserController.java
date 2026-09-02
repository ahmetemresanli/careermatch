package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.UserCreateRequest;
import com.ahmetemresanli.backend.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {

    ResponseEntity<UserResponse> createUser(
            UserCreateRequest request
    );

    ResponseEntity<UserResponse> getUserById(
            Long id
    );

    ResponseEntity<List<UserResponse>> getAllUsers();
}
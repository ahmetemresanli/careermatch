package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IUserController;
import com.ahmetemresanli.backend.dto.request.UserCreateRequest;
import com.ahmetemresanli.backend.dto.response.UserResponse;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.mapper.UserMapper;
import com.ahmetemresanli.backend.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserControllerImpl
        implements IUserController {

    private final IUserService userService;

    public UserControllerImpl(
            IUserService userService
    ) {
        this.userService = userService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request
    ) {

        User user =
                UserMapper.toEntity(request);

        User createdUser =
                userService.createUser(user);

        UserResponse response =
                UserMapper.toResponse(createdUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("@access.isSelf(#id)")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {

        User user =
                userService.getUserById(id);

        return ResponseEntity.ok(
                UserMapper.toResponse(user)
        );
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>>
    getAllUsers() {

        List<UserResponse> responses =
                userService.getAllUsers()
                        .stream()
                        .map(UserMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}

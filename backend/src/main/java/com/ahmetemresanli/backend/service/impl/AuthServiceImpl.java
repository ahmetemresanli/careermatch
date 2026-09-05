package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.dto.request.LoginRequest;
import com.ahmetemresanli.backend.dto.request.RegisterRequest;
import com.ahmetemresanli.backend.dto.response.AuthResponse;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.enums.UserRole;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.InvalidCredentialsException;
import com.ahmetemresanli.backend.mapper.UserMapper;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.security.JwtService;
import com.ahmetemresanli.backend.service.IAuthService;
import com.ahmetemresanli.backend.service.IUserService;
import com.ahmetemresanli.backend.service.IAccountRecoveryService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final IAccountRecoveryService accountRecoveryService;

    public AuthServiceImpl(IUserService userService, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, JwtService jwtService,
                           IAccountRecoveryService accountRecoveryService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.accountRecoveryService = accountRecoveryService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (request.role() == UserRole.ADMIN) {
            throw new BusinessException("Admin accounts cannot be created through public registration");
        }
        User user = new User();
        user.setEmail(request.email());
        user.setPasswordHash(request.password());
        user.setRecoveryEmail(request.recoveryEmail());
        user.setRole(request.role());
        User saved = userService.createUser(user);
        accountRecoveryService.issueEmailVerification(saved.getId());
        return response(saved);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.email().trim())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        if (!user.isActive() || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return response(user);
    }

    private AuthResponse response(User user) {
        return new AuthResponse(jwtService.createToken(user), "Bearer", jwtService.getExpirationSeconds(),
                UserMapper.toResponse(user));
    }
}

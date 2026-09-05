package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {

        String email = user.getEmail().trim().toLowerCase();
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new DuplicateResourceException(
                    "Email already in use"
            );
        }

        user.setEmail(email);
        if (user.getRecoveryEmail() != null && !user.getRecoveryEmail().isBlank()) {
            user.setRecoveryEmail(user.getRecoveryEmail().trim().toLowerCase());
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new com.ahmetemresanli.backend.exception.BusinessException("Password cannot be empty");
        }
        if (!user.getPasswordHash().startsWith("$2")) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already in use"
            );
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
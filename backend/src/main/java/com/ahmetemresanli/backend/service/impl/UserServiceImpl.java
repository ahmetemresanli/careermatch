package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email already in use");
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

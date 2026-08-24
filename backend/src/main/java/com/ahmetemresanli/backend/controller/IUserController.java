package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {

    ResponseEntity<User> createUser(User user);

    ResponseEntity<User> getUserById(Long id);

    ResponseEntity<List<User>> getAllUsers();
}

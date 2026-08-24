package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.User;

import java.util.List;

public interface IUserService {

    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();
}

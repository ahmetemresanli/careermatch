package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IUserController;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements IUserController {

    private final IUserService userService;

    public UserControllerImpl(IUserService userService){
        this.userService = userService;
    }

    @Override
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        User savedUser = userService.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
}

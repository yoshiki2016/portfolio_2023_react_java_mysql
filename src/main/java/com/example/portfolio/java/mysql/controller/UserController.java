package com.example.portfolio.java.mysql.controller;

import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    private UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findUsers() {
        return userService.findUsers();
    }
}

package com.example.portfolio.java.mysql.controller;

import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.form.LoginForm;
import com.example.portfolio.java.mysql.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;

    private UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public LoginResponse login(@RequestBody @Validated LoginForm loginForm) {
        return userService.login(loginForm.getUsername(), loginForm.getPassword());
    }
}

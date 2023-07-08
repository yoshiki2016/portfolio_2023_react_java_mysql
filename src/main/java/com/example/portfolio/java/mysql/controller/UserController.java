package com.example.portfolio.java.mysql.controller;

import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.entity.UserForm;
import com.example.portfolio.java.mysql.form.LoginForm;
import com.example.portfolio.java.mysql.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;

    private UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public LoginResponse login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm.getUsername(), loginForm.getPassword());
    }

    @PostMapping("/user/register")
    public ResponseEntity<Map<String, String>> userRegister(@RequestBody UserForm userForm, UriComponentsBuilder uriBuilder) {
        User user = userService.userRegister( userForm.getGivenName(), userForm.getFamilyName(),
                        userForm.getUserName(), userForm.getPassword(), userForm.getEmail() );
        URI url = uriBuilder
                .path("/user/" + user.getId())
                .build()
                .toUri();
        return ResponseEntity.created(url).body(Map.of("message", "the user successfully created"));
    }
}

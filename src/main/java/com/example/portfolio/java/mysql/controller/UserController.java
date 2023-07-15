package com.example.portfolio.java.mysql.controller;

import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.form.UserForm;
import com.example.portfolio.java.mysql.form.LoginForm;
import com.example.portfolio.java.mysql.form.UserUpdateForm;
import com.example.portfolio.java.mysql.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public LoginResponse login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm.getUserName(), loginForm.getPassword());
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

    @GetMapping("/user_setting/{id}")
    public User findUserById(@PathVariable("id") int id){
        return userService.findUserById(id);
    }

    @PatchMapping("/user_setting")
    public ResponseEntity<Map<String, String>> updateUser(@RequestBody UserUpdateForm userUpdateForm){
        userService.updateUser(userUpdateForm.getUserId(), userUpdateForm.getGivenName(), userUpdateForm.getFamilyName(), userUpdateForm.getUserName(),
                userUpdateForm.getShowPasswordFlag(), userUpdateForm.getPassword(), userUpdateForm.getNewPassword(), userUpdateForm.getEmail() );
        return ResponseEntity.ok(Map.of("message", "the user successfully updated"));
    }
}

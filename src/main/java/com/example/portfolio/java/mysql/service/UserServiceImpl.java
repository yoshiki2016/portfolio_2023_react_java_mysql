package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.LoginResponse;
import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public LoginResponse login(String username, String password){
        Optional<User> loginUser = userMapper.login(username, password);
        if(loginUser.isPresent()){
            int userId = loginUser.get().getId();
            return new LoginResponse(userId, true);
        } else {
            return new LoginResponse(null , false);
        }
    }

    @Override
    public User userRegister(String givenName, String familyName, String username, String password, String email){
        User user = new User(givenName, familyName, username, password, email);
        return userMapper.userRegister(user);
    }
}

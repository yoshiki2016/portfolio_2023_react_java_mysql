package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.LoginResponse;
import com.example.portfolio.java.mysql.entity.User;

import java.util.List;

public interface UserService {
    LoginResponse login(String userName, String password);
    User userRegister(String givenName, String familyName, String userName, String password, String email);

    User findUserById(int id);
}

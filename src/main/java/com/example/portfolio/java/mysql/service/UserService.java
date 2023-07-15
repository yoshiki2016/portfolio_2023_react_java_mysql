package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.LoginResponse;
import com.example.portfolio.java.mysql.entity.User;

public interface UserService {
    LoginResponse login(String userName, String password);
    User userRegister(String givenName, String familyName, String userName, String password, String email);

    User findUserById(int id);

    void updateUser(int userId,String givenName, String familyName, String userName,
                              Boolean showPasswordFlag, String password, String newPassword, String email);
}

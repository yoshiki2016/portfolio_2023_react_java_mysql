package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.entity.User;

import java.util.List;

public interface UserService {
    List<User> findUsers();
}

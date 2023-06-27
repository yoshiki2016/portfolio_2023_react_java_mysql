package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findUsers(){
        return userMapper.findUsers();
    }
}

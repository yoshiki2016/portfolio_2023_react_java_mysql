package com.example.portfolio.java.mysql.mapper;


import com.example.portfolio.java.mysql.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> login(String username, String password);
    User userRegister(User user);
}

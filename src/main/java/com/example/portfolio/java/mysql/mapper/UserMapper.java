package com.example.portfolio.java.mysql.mapper;


import com.example.portfolio.java.mysql.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUsers();
}

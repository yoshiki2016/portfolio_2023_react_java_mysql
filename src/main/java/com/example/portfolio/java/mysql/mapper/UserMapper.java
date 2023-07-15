package com.example.portfolio.java.mysql.mapper;


import com.example.portfolio.java.mysql.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> login(String userName, String password);
    void userRegister(User user);

    Optional<User> findUserById(int id);

    Optional<User> searchUser(int userId, String password);

    void updateUserWithPassword(int userId, String givenName, String familyName,
                                String userName, String newPassword, String email);

    void updateUserWithoutPassword(int userId, String givenName, String familyName,
                                String userName, String email);
}

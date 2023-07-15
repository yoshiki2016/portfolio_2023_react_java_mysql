package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.LoginResponse;
import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.exception.ResourceNotFoundException;
import com.example.portfolio.java.mysql.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public LoginResponse login(String userName, String password){
        Optional<User> loginUser = userMapper.login(userName, password);
        if(loginUser.isPresent()){
            int userId = loginUser.get().getId();
            return new LoginResponse(userId, true);
        } else {
            return new LoginResponse(0 , false);
        }
    }

    @Override
    public User userRegister(String givenName, String familyName, String userName, String password, String email){
        User user = new User(givenName, familyName, userName, password, email);
        userMapper.userRegister(user);
        return user;
    }

    @Override
    public User findUserById(int id){
        Optional<User> user = userMapper.findUserById(id);
        if(user.isPresent()){
            // パスワードを空欄で返却する。
            user.get().setPassword("");
            return user.get();
        } else {
            throw new ResourceNotFoundException("resource not found");
        }
    }

    @Override
    public void updateUser(int userId, String givenName, String familyName, String userName,
                           Boolean showPasswordFlag, String password, String newPassword, String email){
        // パスワードの更新有り
        if(showPasswordFlag){
            Optional<User> user = userMapper.searchUser(userId, password);
            if(user.isPresent()){
                // ユーザーIDとパスワードの組み合わせが合致するものがあればパスワード込みでアップデートする。
                userMapper.updateUserWithPassword(userId, givenName, familyName, userName, newPassword, email);
            } else {
                throw new ResourceNotFoundException("resource not found");
            }
        }
        // パスワードの更新無し
        else{
            userMapper.updateUserWithoutPassword(userId, givenName, familyName, userName, email);
        }
    };
}

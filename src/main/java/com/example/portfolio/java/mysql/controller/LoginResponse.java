package com.example.portfolio.java.mysql.controller;

public class LoginResponse {
    private Integer userId;
    private Boolean loginFlag;

    public LoginResponse(Integer userId, Boolean loginFlag) {
        this.userId = userId;
        this.loginFlag = loginFlag;
    }

    public Integer getUserId() {
        return userId;
    }

    public Boolean getLoginFlag() {
        return loginFlag;
    }
}

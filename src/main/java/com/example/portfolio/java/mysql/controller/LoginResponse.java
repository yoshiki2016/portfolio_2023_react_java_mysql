package com.example.portfolio.java.mysql.controller;

import java.util.Objects;

public class LoginResponse {
    private Integer userId;
    private Boolean loginFlag;

    public LoginResponse(Integer userId, Boolean loginFlag) {
        this.userId = userId;
        this.loginFlag = loginFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse that = (LoginResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(loginFlag, that.loginFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, loginFlag);
    }

    public Integer getUserId() {
        return userId;
    }

    public Boolean getLoginFlag() {
        return loginFlag;
    }
}

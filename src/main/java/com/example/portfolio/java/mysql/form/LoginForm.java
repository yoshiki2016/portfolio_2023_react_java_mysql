package com.example.portfolio.java.mysql.form;

public class LoginForm {
    private String userName;

    private String password;

    public LoginForm(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

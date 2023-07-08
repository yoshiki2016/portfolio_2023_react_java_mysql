package com.example.portfolio.java.mysql.form;

import java.util.Objects;

public class UserForm {
    private String givenName;
    private String familyName;
    private String userName;
    private String password;
    private String email;

    public UserForm(String givenName, String familyName, String userName, String password, String email) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

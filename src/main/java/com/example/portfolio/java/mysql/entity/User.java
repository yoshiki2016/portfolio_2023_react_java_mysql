package com.example.portfolio.java.mysql.entity;

import java.util.Objects;

public class User {
    private int id;
    private String givenName;
    private String familyName;
    private String userName;
    private String password;
    private String email;

    public User(int id, String givenName, String familyName, String userName, String password, String email) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(String givenName, String familyName, String userName, String password, String email) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(givenName, user.givenName) && Objects.equals(familyName, user.familyName) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, givenName, familyName, userName, password, email);
    }

    public int getId() {
        return id;
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

    public void setPassword(String password) {
        this.password = password;
    }
}

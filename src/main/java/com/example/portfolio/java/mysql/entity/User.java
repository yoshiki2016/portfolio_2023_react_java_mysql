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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(givenName, user.givenName)) return false;
        if (!Objects.equals(familyName, user.familyName)) return false;
        if (!Objects.equals(userName, user.userName)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (givenName != null ? givenName.hashCode() : 0);
        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
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
}

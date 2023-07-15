package com.example.portfolio.java.mysql.form;

public class UserUpdateForm {
    private int userId;
    private String givenName;
    private String familyName;
    private String userName;
    private Boolean showPasswordFlag;
    private String password;
    private String newPassword;
    private String email;

    public UserUpdateForm(int userId, String givenName, String familyName, String userName,
                          Boolean showPasswordFlag, String password, String newPassword, String email) {
        this.userId = userId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.showPasswordFlag = showPasswordFlag;
        this.password = password;
        this.newPassword = newPassword;
        this.email = email;
    }

    public int getUserId() {
        return userId;
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

    public Boolean getShowPasswordFlag() {
        return showPasswordFlag;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getEmail() {
        return email;
    }
}

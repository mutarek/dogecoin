package com.dogearn.dogemoney;

public class UserInfo {
    public String userPhone;
    public String userEmail;
    public String userName;
    public String userPoints;


    public UserInfo() {
    }

    public UserInfo(String userPhone, String userEmail, String userName, String userPoints) {
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPoints = userPoints;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(String userPoints) {
        this.userPoints = userPoints;
    }
}

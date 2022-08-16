package com.myapp.UpkaarApp;

public class Userhelper {

    String Email, Password, confirmPassword, user;

    public Userhelper() {
    }

    public Userhelper(String email, String password, String user) {
        Email = email;
        Password = password;
        this.user = user;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

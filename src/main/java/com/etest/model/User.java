/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

/**
 *
 * @author jetdario
 */
public class User extends Faculty {
    
    private int userId;
    private String username_;
    private String password_;
    private String userType;
    private String assignment;

    public int getUserId() {
        return userId;
    }

    public String getUsername_() {
        return username_;
    }

    public String getPassword_() {
        return password_;
    }

    public String getUserType() {
        return userType;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername_(String username_) {
        this.username_ = username_;
    }

    public void setPassword_(String password_) {
        this.password_ = password_;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }    
    
}

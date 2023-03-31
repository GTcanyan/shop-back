package com.javaweb.hhjrp.model;

import lombok.Data;

@Data
public class Admin {
    private String username;
    private String password;
    private String avatar;

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

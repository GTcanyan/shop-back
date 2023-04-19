package com.javaweb.hhjrp.model;

import lombok.Data;

@Data
public class Admin {
    private int id;
    private String username;
    private String password;
    private String avatar;

    @Override
    public String toString() {
        return "Admin{" +
                " id='"+'\''+
                " username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

package com.javaweb.hhjrp.model;

import lombok.Data;

import java.util.Date;

@Data
public class Shop {

    private int ID;
    private String name;
    private float price;
    private float oldPrice;
    private String description;
    private String img;
    private int sort;
    private String other;
    private int views;
    private int count;
    private Date createTime;
    private Date updateTime;
}

package com.javaweb.hhjrp.dto;


import lombok.Data;

/**
 * 购物车的中间类
 */
@Data
public class ShopCart {

    private int cartId;
    private int shopId;
    private String img;
    private String shopName;
    private int price;
    private int oldPrice;
    private int count;
    private String nickname;

}

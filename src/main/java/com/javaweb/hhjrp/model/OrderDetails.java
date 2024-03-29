package com.javaweb.hhjrp.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetails {
    private int detailId;
    private String orderId;
    private int shopId;
    private int quantity;
    private BigDecimal price;

    public OrderDetails() {}

    public OrderDetails(String orderId, int shopId, int quantity, BigDecimal price) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.quantity = quantity;
        this.price = price;
    }
}

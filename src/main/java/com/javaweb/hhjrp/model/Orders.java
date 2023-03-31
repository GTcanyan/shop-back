package com.javaweb.hhjrp.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Orders {
    private int orderId;
    private int userId;
    private BigDecimal totalPrice;
    private int orderStatus;
    // @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    // private Date orderTime;
    private Timestamp orderTime;

    public Orders() {}

    public Orders(int userId, BigDecimal totalPrice, int orderStatus) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
}

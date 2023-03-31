package com.javaweb.hhjrp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderDTO {
    private int orderId;
    private int userId;
    private BigDecimal totalPrice;
    private int orderStatus;
    private Timestamp orderTime;
    private int shopId;
    private int quantity;
    private BigDecimal price;
    private String name;
    private String img;
    private String description;
}

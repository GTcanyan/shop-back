package com.javaweb.hhjrp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class AdminOrderDTO {
    private String orderId;
    private BigDecimal totalPrice;
    private int orderStatus;
    private Timestamp orderTime;
    private String username;
    private String site;

}

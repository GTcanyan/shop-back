package com.javaweb.hhjrp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailsDTO {
    private String orderId;
    private String name;
    private int quantity;
    private BigDecimal price;
}

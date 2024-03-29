package com.javaweb.hhjrp.service;

import com.javaweb.hhjrp.dto.ShopCart;
import com.javaweb.hhjrp.result.AdminResults;
import com.javaweb.hhjrp.result.Results;

import java.util.List;

public interface OrderService {

    // 获取订单详情
    AdminResults getOrders(Integer offset, Integer limit,int userid);
    // 提交订单
    Results addOrders(List<ShopCart> shopCarts);
}

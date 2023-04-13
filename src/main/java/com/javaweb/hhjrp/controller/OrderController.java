package com.javaweb.hhjrp.controller;

import com.alibaba.fastjson2.JSON;
import com.javaweb.hhjrp.dto.ShopCart;
import com.javaweb.hhjrp.result.AdminResults;
import com.javaweb.hhjrp.result.PageTableRequest;
import com.javaweb.hhjrp.result.Results;
import com.javaweb.hhjrp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // //获取用户订单详情
    // @GetMapping("/getOrders")
    // @ResponseBody
    // public Results getOrders(int userId, String token){
    //     return orderService.getOrders(userId);
    // }
    // @RequestParam(value = "token", required = false) String token
    // 获取用户订单详情
    @GetMapping("/getOrders")
    @ResponseBody
    public AdminResults getOrders(PageTableRequest pageTableRequest,
                                  @RequestParam(value = "userId", required = false) int userId
                                  ){
        pageTableRequest.countOffset();
        return orderService.getOrders(pageTableRequest.getOffset(), pageTableRequest.getLimit(),userId);
    }

    // 提交订单
    @PostMapping("/addOrders")
    @ResponseBody
    public Results addOrder(String shopCarts){
        // 后端接口如何接受对象数组：接收json字符串，利用alibaba的JSON工具可以直接想字符串转化为我们想要的list集合。
        List<ShopCart> shopCartList = JSON.parseArray(shopCarts,ShopCart.class);
        return orderService.addOrders(shopCartList);
    }
}

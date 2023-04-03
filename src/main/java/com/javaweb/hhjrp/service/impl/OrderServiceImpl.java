package com.javaweb.hhjrp.service.impl;

import com.javaweb.hhjrp.dao.CartDao;
import com.javaweb.hhjrp.dao.OrderDao;
import com.javaweb.hhjrp.dto.ShopCart;
import com.javaweb.hhjrp.model.OrderDetails;
import com.javaweb.hhjrp.model.Orders;
import com.javaweb.hhjrp.result.AdminResults;
import com.javaweb.hhjrp.result.Results;
import com.javaweb.hhjrp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartDao cartDao;

    // 获取订单详情
    @Override
    public AdminResults getOrders(Integer offset, Integer limit,int userid){
        return new AdminResults(20000,"获取成功",orderDao.getOrders(offset,limit,userid),orderDao.getOrderCountByUId(userid));
    }

    // 提交订单
    @Override
    public Results addOrders(List<ShopCart> shopCarts) {
        // 根据cartId获取userId
        int userId = cartDao.getUserId(shopCarts.get(0).getCartId());
        // 计算总价格
        float total = 0;
        for (int i = 0; i < shopCarts.size(); i++) {
            total += shopCarts.get(i).getCount()*shopCarts.get(i).getPrice();
        }
        BigDecimal totalPrice = new BigDecimal(total);
        // 创建订单对象
        Orders order = new Orders();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(1);
        // 添加订单
        Results results = new Results(20000,"支付成功",orderDao.addOrder(order));
        // 创建订单详情对象
        OrderDetails orderDetails = new OrderDetails();
        if (results.getCode()==20000){
            for (int i = 0; i < shopCarts.size(); i++) {
                orderDetails.setOrderId(order.getOrderId());
                orderDetails.setShopId(shopCarts.get(i).getShopId());
                orderDetails.setQuantity(shopCarts.get(i).getCount());
                orderDetails.setPrice(new BigDecimal(shopCarts.get(i).getPrice()));
                // 添加订单详情
                // int res = orderDao.addOrderDetails(orderDetails);
                // 成功则根据cartId删除购物车列表的数据
                if (orderDao.addOrderDetails(orderDetails) == 1){
                    cartDao.deleteCartByC(shopCarts.get(i).getCartId());
                }
            }
        }
        return results;
    }
}

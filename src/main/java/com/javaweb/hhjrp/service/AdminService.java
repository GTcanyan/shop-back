package com.javaweb.hhjrp.service;

import com.javaweb.hhjrp.model.Shop;
import com.javaweb.hhjrp.model.Sort;
import com.javaweb.hhjrp.model.User;
import com.javaweb.hhjrp.result.AdminResults;
import com.javaweb.hhjrp.result.Result;
import com.javaweb.hhjrp.result.Results;

import java.util.List;
import java.util.Map;

public interface AdminService {

    // 管理员登录
    Map<String, Object> adminLogin(String username, String password);

    // 获取用户列表信息，包括分页功能
    Result getAllUser(Integer offset, Integer limit,String username,String phone,String site);

    // 添加用户
    Results addUser(String username, String password, String nickname);

    // 删除用户
    Results deleteUser(int userID);

    // 获取所有商品列表，同时分页
    Result getAllShop(Integer offset, Integer limit, String id,String shopname);

    // 添加商品
    Results addShop(Shop shop);

    // 修改商品信息
    Results changeShopInform(Shop shop);

    // 删除商品
    Results deleteShop(int shopID);

    // 获取用户购物车
    AdminResults getAllCart(Integer offset, Integer limit);

    // 获取主页的一些信息， 用户总数和商品总数
    Results getIndex();

    // 修改管理员密码
    Results changePassword(String oldPassword, String newPassword);

    // 修改用户信息
    Results changeUserInfo(User user);

    Map<String, Object> getAdminInfo(String token);

    void logout(String token);

    List<Sort> getSort();

    // 获取轮播图列表
    Result getCarousel();

    Result changeCarousel(int id,int start);

    Result getAllShopList();

    Result addCarousel(int shopId);

    Result getOrderList(Integer offset, Integer limit,String orderId, String status, String site);

    Result getOrderDetails(String orderId);

    Result delivery(String orderId);

    Result drawback(String orderId);
}

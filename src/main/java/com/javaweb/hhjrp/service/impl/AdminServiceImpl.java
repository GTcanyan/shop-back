package com.javaweb.hhjrp.service.impl;

import com.javaweb.hhjrp.dao.AdminDao;
import com.javaweb.hhjrp.dao.UserDao;
import com.javaweb.hhjrp.dto.IndexPage;
import com.javaweb.hhjrp.model.Shop;
import com.javaweb.hhjrp.model.Sort;
import com.javaweb.hhjrp.model.User;
import com.javaweb.hhjrp.result.AdminResults;
import com.javaweb.hhjrp.result.Results;
import com.javaweb.hhjrp.service.AdminService;
import com.javaweb.hhjrp.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;


    // 管理员登录
    @Override
    public Map<String, Object> adminLogin(String username, String password) {
        String adminPassword = adminDao.getAdminPass(username);// 先获取老的密码
        if(MD5.crypt(password).equals(adminPassword)){ // 密码正确
            Map<String, Object> data = new HashMap<>();
            String key = "user:" + UUID.randomUUID();
            data.put("token", key);    // 待优化，最终方案jwt
            // 存入Redis
            redisTemplate.opsForValue().set(key,username,30, TimeUnit.MINUTES);
            return data;
        }
        return null;
    }

    // 获取用户列表信息，包括分页功能
    @Override
    public AdminResults getAllUser(Integer offset, Integer limit) {
        return new AdminResults(20000, "获取成功", adminDao.getAllUser(offset, limit), adminDao.getUserCount());
    }

    // 添加用户
    @Override
    public Results addUser(String username, String password, String nickname) {
        adminDao.addUser(username, MD5.crypt(password), nickname);
        return new Results(20000, "添加成功");
    }

    // 删除用户
    @Override
    public Results deleteUser(int userID) {
        adminDao.deleteUser(userID);
        return new Results(20000, "删除成功");
    }
    @Override
    public List<Sort> getSort() {
        return adminDao.getSort();
    }

    // 获取所有商品列表，同时分页
    @Override
    public AdminResults getAllShop(Integer offset, Integer limit, String id, String shopname) {
        return new AdminResults(20000, "获取成功", adminDao.getAllShop(offset, limit,id,shopname), adminDao.getShopCount());
    }

    // 添加商品
    @Override
    public Results addShop(Shop shop) {
        adminDao.addShop(shop);
        return new Results(20000, "添加成功");
    }

    // 修改商品信息
    @Override
    public Results changeShopInform(Shop shop) {
        adminDao.changeShopInform(shop);
        return new Results(20000, "修改成功");
    }

    // 删除商品
    @Override
    public Results deleteShop(int shopID) {
        adminDao.deleteShop(shopID);
        return new Results(20000, "删除成功");
    }

    // 获取用户购物车
    @Override
    public AdminResults getAllCart(Integer offset, Integer limit) {
        return new AdminResults(20000, "获取成功", adminDao.getAllCart(offset, limit), adminDao.getCartCount());
    }

    // 获取主页的一些信息，获取商品总数和用户总数
    @Override
    public Results getIndex() {
        IndexPage indexPage = new IndexPage();
        Integer userCount = adminDao.getUserCount(); // 用户总数
        Integer shopCount = adminDao.getShopCount(); // 商品总数
        indexPage.setShop(shopCount);
        indexPage.setUser(userCount);
        return new Results(20000, "获取成功", indexPage);
    }

    // 修改密码
    @Override
    public Results changePassword(String oldPassword, String newPassword) {
        // Results results = adminLogin("admin", oldPassword);
        // if(results.getCode() == 1){ // 旧密码正确
        //     adminDao.changePassword(MD5.crypt(newPassword));
        //     return new Results(1, "密码修改成功");
        // }else {
        //     return new Results(0, "旧密码不正确");
        // }
        return null;
    }

    // 修改用户信息
    @Override
    public Results changeUserInfo(User user) {
        userDao.changeUserInfo(user);
        return new Results(20000, "修改用户信息成功");
    }

    @Override
    public Map<String, Object> getAdminInfo(String token) {
        // 根据token获取用户信息，从Redis
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj != null){
            // System.out.println(JSON.toJSONString(obj));
            // Admin admin = JSON.parseObject(JSON.toJSONString(obj),Admin.class);
            Map<String, Object> data = new HashMap<>();
            data.put("username",obj);
            // data.put("password",admin.getPassword());

            //角色
            // List<String> roleList = this.getBaseMapper().getRoleNamesByUserId(loginUser.getId());
            // data.put("roles", roleList);
            return data;
        }
        return null;
    }
    // 后台管理员退出
    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }



}

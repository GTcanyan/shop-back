package com.javaweb.hhjrp.controller;


import com.javaweb.hhjrp.model.Shop;
import com.javaweb.hhjrp.model.Sort;
import com.javaweb.hhjrp.model.User;
import com.javaweb.hhjrp.result.AdminResults;
import com.javaweb.hhjrp.result.PageTableRequest;
import com.javaweb.hhjrp.result.Result;
import com.javaweb.hhjrp.result.Results;
import com.javaweb.hhjrp.service.AdminService;
import com.javaweb.hhjrp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    // 管理员登录
    @PostMapping("/login")
    @ResponseBody
    public Result AdminLogin(String username, String password){
        Map<String,Object> data = adminService.adminLogin(username, password);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getAdminInfo(@RequestParam("token") String token){
        Map<String,Object> data = adminService.getAdminInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"登录信息无效，请重新登录");
    }
    // 管理员注销
    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        adminService.logout(token);
        return Result.success("注销成功");
    }

    // 获取用户列表信息，包括分页功能
    @GetMapping("/getAllUser")
    @ResponseBody
    public AdminResults getAllUser(PageTableRequest pageTableRequest){
        pageTableRequest.countOffset(); // 先计算offset
        // System.out.println(pageTableRequest.toString());
        return adminService.getAllUser(pageTableRequest.getOffset(), pageTableRequest.getLimit());
        // return adminService.getAllUser(5,0);

    }

    // 添加用户
    @PostMapping("/addUser")
    @ResponseBody
    public Results addUser(String username, String password, String nickname){
        return adminService.addUser(username, password, nickname);
    }

    // 修改用户信息
    @PostMapping("/changeUser")
    @ResponseBody
    public Results changeInfo(int userID, User user){
        user.setID(userID);
        return adminService.changeUserInfo(user);
    }

    // 删除用户
    @PostMapping("/deleteUser")
    @ResponseBody
    public Results deleteUser(int userID){
        return adminService.deleteUser(userID);
    }

    // 获取商品类型
    @GetMapping("/getSort")
    @ResponseBody
    public Result getSort(){
        List<Sort> data = adminService.getSort();
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");

    }
    // 获取所有商品列表
    @GetMapping("/getAllShopList")
    @ResponseBody
    public Result getAllShopList(){
        return adminService.getAllShopList();
    }
    // 获取所有商品列表，同时分页
    @GetMapping("/getAllShop")
    @ResponseBody
    public AdminResults getAllShop(PageTableRequest pageTableRequest,
                                   @RequestParam(value = "id", required = false) String strId,
                                   @RequestParam(value = "shopName", required = false) String shopName){
        pageTableRequest.countOffset();
        return adminService.getAllShop(pageTableRequest.getOffset(), pageTableRequest.getLimit(),strId,shopName);
    }

    // 添加商品
    @PostMapping("/addShop")
    @ResponseBody
    public Results addShop(Shop shop){
        return adminService.addShop(shop);
    }

    // 修改商品信息
    @PostMapping("/changeShop")
    @ResponseBody
    public Results changeShop(int shopID, Shop shop){
        shop.setID(shopID);
        return adminService.changeShopInform(shop);
    }

    // 删除商品
    @PostMapping("/deleteShop")
    @ResponseBody
    public Results deleteShop(int shopID){
        return adminService.deleteShop(shopID);
    }

    // 获取用户购物车
    @GetMapping("/getAllCart")
    @ResponseBody
    public AdminResults getAllCart(PageTableRequest pageTableRequest){
        pageTableRequest.countOffset();
        return adminService.getAllCart(pageTableRequest.getOffset(), pageTableRequest.getLimit());
    }

    // 获取主页的一些信息，用户总数和商品总数
    @GetMapping("/getIndex")
    @ResponseBody
    public Results getIndex(){
        return adminService.getIndex();
    }

    // 修改管理员密码
    @PostMapping("/changePassword")
    @ResponseBody
    public Results changePassword(String oldPassword, String newPassword) {
        return adminService.changePassword(oldPassword, newPassword);
    }
    // 获取轮播图数据
    @PostMapping("/getCarousel")
    @ResponseBody
    public Result getCarousel(){
        return adminService.getCarousel();
    }
    // 启停轮播图
    @PostMapping("/changeCarousel")
    @ResponseBody
    public Result changeCarousel(int id,int start){
        return adminService.changeCarousel(id,start);
    }
    @PostMapping("/addCarousel")
    @ResponseBody
    public Result addCarousel(int shopId){
        return adminService.addCarousel(shopId);
    }


}

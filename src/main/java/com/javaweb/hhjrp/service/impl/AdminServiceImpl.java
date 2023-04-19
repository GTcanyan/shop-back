package com.javaweb.hhjrp.service.impl;

import com.javaweb.hhjrp.dao.AdminDao;
import com.javaweb.hhjrp.dao.OrderDao;
import com.javaweb.hhjrp.dao.UserDao;
import com.javaweb.hhjrp.dto.AdminOrderDTO;
import com.javaweb.hhjrp.dto.IndexPage;
import com.javaweb.hhjrp.dto.ShopSort;
import com.javaweb.hhjrp.model.Carousel;
import com.javaweb.hhjrp.model.Shop;
import com.javaweb.hhjrp.model.Sort;
import com.javaweb.hhjrp.model.User;
import com.javaweb.hhjrp.result.AdminResults;
import com.javaweb.hhjrp.result.Result;
import com.javaweb.hhjrp.result.Results;
import com.javaweb.hhjrp.service.AdminService;
import com.javaweb.hhjrp.util.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


@Api(value = "管理员控制接口，tags = 测试接口1")
@Service
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisTemplate redisTemplate;


    // 管理员登录
    @ApiOperation("登录页面")
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
    public Result getAllUser(Integer offset, Integer limit,String username,String phone,String site) {
        Map<String, Object> resultMap = new HashMap<>();
        List<User> allUser = adminDao.getAllUser(offset, limit, username,phone,site);
        final Integer userTotal = adminDao.getUserTotal(username, phone, site);
        resultMap.put("list",allUser);
        resultMap.put("total",userTotal);
        return new Result(20000, "获取成功",resultMap);
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

    // 获取轮播图列表
    @Override
    public Result getCarousel() {

        return Result.success(adminDao.getCarousel());
    }

    // 启停轮播图
    @Override
    public  Result changeCarousel(int id,int start){
        if (start==1){
            if (adminDao.changeCarousel(id,start)==1)
                return Result.success("成功启用该轮播图");
            else
                return Result.fail("修改失败");
        }else {
            if (adminDao.changeCarousel(id,start)==1)
                return Result.success("停用该轮播图");
            else
                return Result.fail("修改失败");
        }

    }

    // 新增轮播图
    @Override
    public Result addCarousel(int shopId) {
        Shop shop = adminDao.getShop(shopId);
        Carousel carousel = adminDao.getCarouselByShopId(shopId);
        // System.out.println(carousel.isStart());
        if (shop==null){
            return Result.fail("商品不存在！");
        }else if(carousel != null){
            //    加一个判断如果轮播图表已有该商品，则判断是否为开启状态。变为启用轮播图，而不是新增轮播图**********
            if (carousel.isStart()) {
                return Result.fail("已存在轮播图，并且已开启");
            }else {

                return Result.success(adminDao.changeCarousel(carousel.getID(),1),"重复添加咯，自动帮您开启~");
            }
        }
        else {
            String str = shop.getImg();
            String substr = "";
            int endIndex = str.indexOf("&&"); // 获取逗号的位置
            if (endIndex != -1) { // 如果找到逗号
                substr = str.substring(0, endIndex); // 截取从第 0 个位置到&&之前的子字符串
            }else {
                substr=str;
            }
            if (adminDao.addCarousel(shop.getSort(),substr,shop.getID())==1) {
                return Result.success("新增成功");
            }else {
                return Result.fail("新增失败");
            }
        }
    }


    // 获取所有商品
    @Override
    public Result getAllShopList() {
        return Result.success(adminDao.getAllShopList());
    }


    // 获取所有商品列表，同时分页
    @Override
    public Result getAllShop(Integer offset, Integer limit, String id, String shopname) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ShopSort> allShop = adminDao.getAllShop(offset, limit, id, shopname);
        Integer shopCount = adminDao.getShopTotal(id, shopname);
        resultMap.put("list",allShop);
        resultMap.put("total",shopCount);
        return new Result(20000, "获取成功",resultMap );
    }

    // 添加商品
    @Override
    public Results addShop(Shop shop) {
        if (shop.getOldPrice() >= shop.getPrice()) {
            adminDao.addShop(shop);
            return new Results(20000, "添加成功");
        }else {
            return new Results(20001, "添加失败，优惠价大于原价格");
        }

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

    // 获取订单列表
    @Override
    public Result getOrderList(Integer offset, Integer limit, String orderId, String status, String site) {
        // orderDao.getOrderList(offset,limit,orderId,status,site);
        // System.out.println(orderDao.getOrderList(offset,limit,orderId,status,site));
        Map<String, Object> resultMap = new HashMap<>();
        List<AdminOrderDTO> orderList = orderDao.getOrderList(offset, limit, orderId, status, site);
        int total = orderDao.getOrderTotal(orderId, status, site);
        resultMap.put("list",orderList);
        resultMap.put("total",total);
        return Result.success(resultMap);
    }

    // 获取订单商品列表
    @Override
    public Result getOrderDetails(String orderId) {
        return Result.success(orderDao.getOrderDetails(orderId));
    }

    @Override
    public Result delivery(String orderId) {
        if (orderDao.delivery(orderId) == 1) {
            return Result.success(20000,"发货成功");
        }
        return Result.fail(20001,"发货失败");
    }

    @Override
    public Result drawback(String orderId) {
        if (orderDao.drawback(orderId) == 1) {
            return Result.success(20000,"退款成功");
        }
        return Result.fail(20001,"退款失败");
    }

}

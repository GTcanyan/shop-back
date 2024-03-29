package com.javaweb.hhjrp.dao;

import com.javaweb.hhjrp.dto.ShopCart;
import com.javaweb.hhjrp.dto.ShopSort;
import com.javaweb.hhjrp.model.Carousel;
import com.javaweb.hhjrp.model.Shop;
import com.javaweb.hhjrp.model.Sort;
import com.javaweb.hhjrp.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminDao {

    // 获取管理员的密码
    @Select("select password from admin where username = #{username} ")
    String getAdminPass(String username);

    // 获取管理员信息
    // @Select()
    // <Admin> getAdminInfo();

    // 获取所有用户信息，同时分页
    // @Select("select ID,username,phone,sign,site,nickname from user limit #{limit} offset #{offset}")
    List<User> getAllUser(Integer offset, Integer limit,String username,String phone,String site);

    // 获取用户总数
    @Select("select count(username) from user ")
    Integer getUserCount();

    Integer getUserTotal(String username,String phone,String site);

    // 添加用户
    @Insert("insert into user(username, password, nickname, site) values(#{username}, #{password}, #{nickname}, '中国')")
    void addUser(String username, String password, String nickname);

    // 删除用户
    @Delete("delete from user where ID = #{userID} ")
    void deleteUser(int userID);

    // 获取所有商品，不分页
    @Select({"select ID,name from shop"})
    List<Shop> getAllShopList();

    // 获取商品总数
    @Select("select count(ID) from shop")
    Integer getShopCount();

    int getShopTotal(String id, String shopname);

    // 获取所有商品信息，同时分页
    // @Select("select a.*,b.sortname from shop a LEFT JOIN sort b ON a.sort=b.sortID  WHERE 1=1"
    //         + " <if test='id != null'>AND CONCAT(id) like CONCAT('%', #{id}, '%')</if>"
    //         + " <if test='shopname != null'>AND name LIKE CONCAT('%', #{shopname}, '%')</if>" +
    //         " limit #{limit} offset #{offset}")
    List<ShopSort> getAllShop(Integer offset, Integer limit, String id, String shopname);

    // 添加商品
    @Insert("insert into shop(name, price, old_price, description, img, sort, other,count) values(#{name}, #{price}, #{oldPrice}, #{description}, #{img}, #{sort}, #{other},#{count})")
    void addShop(Shop shop);

    // 修改商品信息
    void changeShopInform(Shop shop);

    // 删除商品
    @Delete("delete from shop where ID = #{shopID}")
    void deleteShop(int shopID);

    // 获取购物车总数
    @Select("select count(user_id) from cart")
    Integer getCartCount();

    // 获取所有购物车
    @Select("select t1.*, t2.nickname " +
            "from  " +
            "(select cart.ID as cartId,cart.shop_id as shopId, shop.img, shop.name as shopName, shop.price, shop.old_price as oldPrice, cart.count, cart.user_id " +
            "from cart LEFT JOIN shop on cart.shop_id = shop.ID) " +
            " as t1 LEFT JOIN `user` as t2  " +
            "on t1.user_id = t2.ID ")
    List<ShopCart> getAllCart(Integer offset, Integer limit);

    // 修改管理员密码
    @Update("update admin set password = #{crypt} where username = 'admin' ")
    void changePassword(String crypt);

    @Select("SELECT * FROM sort")
    List<Sort> getSort();

    @Select("SELECT * FROM carousel ")
    List<Carousel> getCarousel();

    // 重载getCarousel()
    @Select("SELECT * FROM carousel WHERE shop_id = #{shopId} ")
    Carousel getCarouselByShopId(int shopId);

    @Update("UPDATE carousel set `start`=#{start} where id = #{id}")
    int changeCarousel(int id,int start);


    @Select("select * from shop where ID = #{shopId}")
    Shop getShop(int shopId);

    @Insert("INSERT INTO `carousel` (`sort`, `img`, `shop_id`) VALUES (#{sort},#{img},#{id})")
    int addCarousel(int sort, String img, int id);
}

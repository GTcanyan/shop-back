package com.javaweb.hhjrp.dao;

import com.javaweb.hhjrp.model.Carousel;
import com.javaweb.hhjrp.model.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShopDao {


    // 根据商品id获取商品详情信息
    @Select("select * from shop where ID = #{shopID} ")
    Shop getShopInfoById(int shopID);

    // 获取所有商品
    @Select("select * from shop")
    List<Shop> getAllShop();

    // 获取某种类型商品的列表页面
    @Select("select * from shop where sort = #{type} ")
    List<Shop> getShopList(int type);

    // 通过商品名字模糊查询
    @Select("select * from shop where name like '%${key}%'")
    List<Shop> searchShopByShopName(String key);

    // 获取某种商品列表的最上面的轮播图
    @Select("select * from carousel where sort = #{type} AND start != 0 ")
    List<Carousel> getCarouselList(int type);

    @Update("UPDATE shop SET views = views + 1 WHERE id = #{id}")
    int addViews(int id);
}

package com.javaweb.hhjrp.dao;

import com.javaweb.hhjrp.dto.OrderDTO;
import com.javaweb.hhjrp.model.OrderDetails;
import com.javaweb.hhjrp.model.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDao {

    @Select("SELECT o.*,od.shop_id,od.quantity,od.price,s.`name`,s.img,s.description " +
            "FROM orders o " +
            "LEFT JOIN order_details od ON od.order_id = o.order_id " +
            "LEFT JOIN shop s ON s.ID = od.shop_id " +
            "WHERE o.user_id = #{ userid } limit #{limit} offset #{offset}")
    List<OrderDTO> getOrders(Integer offset, Integer limit,int userid);
    // 获取订单总条数
    @Select("SELECT count(o.order_id) " +
            "FROM orders o " +
            "LEFT JOIN order_details od ON od.order_id = o.order_id " +
            "LEFT JOIN shop s ON s.ID = od.shop_id " +
            "WHERE o.user_id = #{ userid }")
    Integer getOrderCountByUId(int userId);

    @Insert("INSERT INTO orders(user_id, total_price,order_status) VALUES (#{userId},#{totalPrice},#{orderStatus})")
    // @Insert插入数据时返回自增主键order_id, order_id值被反填到order对象中，调用getOrderId()就可以获取
    @Options(useGeneratedKeys=true, keyProperty="orderId", keyColumn="order_id")
    int addOrder(Orders order);

    @Insert("INSERT INTO order_details(order_id, shop_id, quantity, price) VALUES (#{orderId}, #{shopId}, #{quantity}, #{price})")
    int addOrderDetails(OrderDetails orderDetails);
}

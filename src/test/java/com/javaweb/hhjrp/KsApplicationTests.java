package com.javaweb.hhjrp;

import com.javaweb.hhjrp.dao.CartDao;
import com.javaweb.hhjrp.dao.OrderDao;
import com.javaweb.hhjrp.dao.UserDao;
import com.javaweb.hhjrp.util.TokenVerify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class KsApplicationTests {

	@Autowired
	UserDao userDao;

	@Autowired
	CartDao cartDao;

	@Autowired
	OrderDao orderDao;

	@Test
	void getOrders(){
		System.out.println(orderDao.getOrders(14));
		// System.out.println(new Date());
	}
	@Test
	void  getCartsByUserID(){
		System.out.println(cartDao.getCartsByUserID(14));
	}

	@Autowired
	TokenVerify tokenVerify;

	@Test
	void contextLoads() {
		System.out.println( UUID.randomUUID().toString().replace("-", "").toLowerCase());
	}
}

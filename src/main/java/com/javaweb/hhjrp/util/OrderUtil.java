package com.javaweb.hhjrp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class OrderUtil {
    // 订单号前缀
    private static final String PREFIX = "PJ";
    // 生成唯一订单号
    public static String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date()); // 获取当前时间戳，精确到毫秒
        Random random = new Random();
        int randomNum = random.nextInt(1000); // 生成一个3位随机数
        return PREFIX + timestamp + randomNum; // 拼接订单号
    }
}

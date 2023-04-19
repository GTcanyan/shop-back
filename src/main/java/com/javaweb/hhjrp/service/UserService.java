package com.javaweb.hhjrp.service;

import com.javaweb.hhjrp.model.User;
import com.javaweb.hhjrp.result.Results;

public interface UserService {

    // 根据用户id查询用户
    User getUserById(Integer id);

    // 用户注册
    Results userRegister(String username, String password, String nickname,String phone);

    // 用户登录
    Results userLogin(String username, String password);

    // 获取用户信息
    Results getInfo(int userID, String token);

    // 修改用户信息
    Results changeUserInfo(User user);

    // 修改用户密码
    Results changePassword(int id, String token, String oldPassword, String password);

    // 获取用户地址
    Results getUserSite(int userID);

}

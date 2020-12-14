package com.bxoon.controller;

import com.bxoon.model.SysLoginModel;
import com.bxoon.util.JwtUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：GuangxiZhong
 * @date ：Created in 2020/12/14 9:58
 * @description：登录Controller
 * @modified By：
 * @version: 1.0
 */
@RestController
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody SysLoginModel sysLoginModel) throws Exception {
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();
        // 通过查询数据库校验用户名是否存在，本处模拟数据库校验
        if (!"zhangsan".equals(username)){
            return "用户不存在";
        }
        // 校验密码是否正确
        if (!"12345".equals(password)){
            return "密码不正确";
        }
        // 生成token
        String token = JwtUtil.sign(username, password);

        // 把token放到redis里面并设置过期时间
        // redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        // 设置超时时间
        // redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);

        return "登录成功，token为"+token;
    }
}

package com.bxoon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：GuangxiZhong
 * @date ：Created in 2020/12/14 10:34
 * @description：主页
 * @modified By：
 * @version: 1.0
 */
@RestController
public class HomeController {

    @RequestMapping("home")
    public String home(){
        return "资源请求成功";
    }
}

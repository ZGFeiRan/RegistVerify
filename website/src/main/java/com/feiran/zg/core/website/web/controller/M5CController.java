package com.feiran.zg.core.website.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * 模拟短信网关
 * Created by zhangguangfei on 2017/1/17.
 */
@Controller
public class M5CController extends BaseController{

    @RequestMapping("/send")
    @ResponseBody
    public String send(String userName,String password,String apikey,String mobile,String content){
        System.out.println("发送短信给手机号为: "+mobile+" 发送内容为"+content);
        return "success:"+ UUID.randomUUID().toString();
    }
}

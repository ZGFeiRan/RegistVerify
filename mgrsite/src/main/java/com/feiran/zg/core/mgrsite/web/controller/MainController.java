package com.feiran.zg.core.mgrsite.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 访问后台主界面的控制器
 * Created by zhangguangfei on 2017/1/15.
 */
@Controller
public class MainController {

    @RequestMapping("/index")
    public String index(){
        return "main";
    }
}

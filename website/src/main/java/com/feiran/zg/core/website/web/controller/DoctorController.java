package com.feiran.zg.core.website.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by FeiRan_ZG on 2017/8/14.
 *
 * 医生控制器
 */
@Controller
public class DoctorController {
    @RequestMapping("newDoctor")
    public String newDoctor(){

        return "newDoctor";
    }
}

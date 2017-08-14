package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.service.IEmailService;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by zhangguangfei on 2017/1/17.
 */
@Controller
public class SendEmailController {

    @Autowired
    private IEmailService emailService;

    @RequestMapping("/sendEmail")
    @ResponseBody
    public JsonResult sendEmail(String email){
        JsonResult jsonResult = new JsonResult();
        try {
            emailService.sendVerifyEmail(email);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
}

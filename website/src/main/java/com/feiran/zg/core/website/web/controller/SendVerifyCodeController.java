package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.service.IVerifyCodeService;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 发送验证码控制器
 * Created by zhangguangfei on 2017/1/15.
 */
@Controller
public class SendVerifyCodeController {

    @Autowired
    private IVerifyCodeService verifyCodeService;

    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public JsonResult sendVerifyCode(String phoneNumber){
        JsonResult jsonResult = new JsonResult();
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage()  );
        }
        return jsonResult;
    }
}

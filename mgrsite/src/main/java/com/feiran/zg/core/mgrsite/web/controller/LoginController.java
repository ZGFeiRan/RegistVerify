package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.service.ILoginInfoService;
import com.feiran.zg.core.base.domain.LoginInfo;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登录
 * Created by zhangguangfei on 2017/1/15.
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginInfoService loginInfoService;

    /***
     * 后台管理员登录
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String userName, String password, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        System.out.println("断点调试");
        LoginInfo loginInfo = this.loginInfoService.login(userName, password, request.getRemoteAddr(), LoginInfo.USER_MANAGER);

        if (loginInfo==null){
            jsonResult.setSuccess(false);
            jsonResult.setMsg("用户名或密码错误");
        }
        return jsonResult;
    }
}

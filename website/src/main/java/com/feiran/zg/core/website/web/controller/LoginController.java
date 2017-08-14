package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.service.ILoginInfoService;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.base.domain.LoginInfo;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangguangfei on 2017/1/18.
 */
@Controller
public class LoginController {
    @Autowired
    private ILoginInfoService loginInfoService;

    /***
     * 客户端用户登录
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String userName, String password, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();
        String ip = request.getRemoteAddr();// 获取当前登录用户的登录ip地址
        LoginInfo loginInfo = this.loginInfoService.login(userName, password,ip,LoginInfo.USER_CLIENT );
        if (loginInfo==null){
            jsonResult.setSuccess(false);
            jsonResult.setMsg("用户名或密码错误");
        }
        return jsonResult;
    }

    /***
     * 客户端用户退出
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpServletResponse response){
        UserContext.putCurrent(null);
        try {
            response.sendRedirect("/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

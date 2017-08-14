package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.service.ILoginInfoService;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台退出
 * Created by zhangguangfei on 2017/1/15.
 */
@Controller
public class LogoutController {

    @Autowired
    private ILoginInfoService loginInfoService;

    /***
     * 后台管理员退出
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String login(String userName, String password, HttpServletRequest request) {
        UserContext.putCurrent(null);
        return "redirect:/login.html";
    }
}

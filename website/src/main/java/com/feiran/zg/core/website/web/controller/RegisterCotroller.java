package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.service.ILoginInfoService;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by zhangguangfei on 2017/1/11.
 */
@Controller
public class RegisterCotroller {
    @Autowired
    private ILoginInfoService loginInfoService;

    /***
     * 新用户注册
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public JsonResult register(String userName,String password){
        JsonResult result = new JsonResult();
        try {
            this.loginInfoService.register(userName,password);
            result.setMsg("注册成功");
        }catch (RuntimeException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("注册失败,原因是:"+e.getMessage()+",如有需要请联系管理员");
        }
        return result;
    }

    /***
     * 检查用户名是否已经被注册过
     * @param username
     * @return
     */
    @RequestMapping("/checkUserName")
    @ResponseBody
    public boolean checkUserName(String username){
        return ! this.loginInfoService.checkUserNameExist(username);
    }

}

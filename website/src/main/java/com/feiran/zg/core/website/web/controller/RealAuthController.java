package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.service.IDoctorInfoService;
import com.feiran.zg.core.base.service.IRealAuthService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.website.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * 实名认证相关的控制器
 * Created by zhangguangfei on 2017/2/3.
 */
@Controller
public class RealAuthController extends BaseController{

    @Autowired
    private IDoctorInfoService doctorInfoService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private ServletContext servletContext;

    @RequireLogin("实名认证")
    @RequestMapping("realAuth")
    public String realAuth(Model model){
        // 得到当前的用户
        DoctorInfo current = this.doctorInfoService.getCurrent();
        // 如果用户没有实名认证,并且realAuthId为空,则导向到realAuth页面
        if (!current.getIsRealAuth() && current.getRealAuthId() == null){
            return "realAuth";
        }else {
            // 如果用户提交了实名认证,但还没有通过后台审核通过的时候,导向到realAuth_result页面,并设置"auditing"为true
            if (!current.getIsRealAuth()){
                model.addAttribute("auditing",true);
            }else {// 如果用户提交了实名认证,并且通过了后台审核导向到realAuth_result页面,并设置"auditing"为false
                model.addAttribute("auditing",false);
                model.addAttribute("test");
                model.addAttribute("realAuth",this.realAuthService.getById(current.getRealAuthId()));
            }
            return "realAuth_result";
        }
    }

    @RequestMapping("realAuth_save")
    @ResponseBody
    public JsonResult realAuthSave(RealAuth realAuth){
        JsonResult jsonResult = new JsonResult();

        this.realAuthService.apply(realAuth);

        return jsonResult;
    }

//    @RequireLogin("上传证件的正面照片")  这里不能贴这个标签
    @RequestMapping("realAuthUpload")
    @ResponseBody
    public String realAuthUpload(MultipartFile file){
        String dic = "/upload";
        String basePath = this.servletContext.getRealPath(dic);
        String fileName = UploadUtil.upload(file, basePath, doctorInfoService);
        return dic+"/"+fileName;
    }
}

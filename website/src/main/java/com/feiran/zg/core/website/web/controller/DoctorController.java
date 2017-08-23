package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.domain.DoctorDegree;
import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.DoctorTitle;
import com.feiran.zg.core.base.domain.RealAuth;
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
import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/14.
 *
 * 医生控制器
 */
@Controller
public class DoctorController {

    @Autowired
    private IDoctorInfoService doctorInfoService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private ServletContext servletContext;

    @RequireLogin("完善个人资料时,回显用户基本资料")
    @RequestMapping("fillDoctorBaseInfo")
    public String fillDoctorBaseInfo(Model model){
        DoctorInfo doctorInfo = this.doctorInfoService.getCurrent();
        List<DoctorTitle> doctorTitleList = this.doctorInfoService.getDoctorTitle();
        List<DoctorDegree> doctorDegreeList = this.doctorInfoService.getDoctorDegree();

        RealAuth realAuth = this.realAuthService.getById(doctorInfo.getRealAuthId());

        model.addAttribute("doctorInfo",doctorInfo);
        model.addAttribute("realAuth",realAuth);
        model.addAttribute("doctorTitleList",doctorTitleList);
        model.addAttribute("doctorDegreeList",doctorDegreeList);
        return "fillDoctorBaseInfo";
    }

    /**
     * 保存/修改用户信息
     */
    @RequireLogin("保存/修改用户信息")
    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JsonResult basicInfoSave(DoctorInfo doctorInfo){
        JsonResult jsonResult = new JsonResult();

        try {
            this.doctorInfoService.update(doctorInfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }


    //    @RequireLogin("上传证件的正面照片")  这里不能贴这个标签
    @RequestMapping("doctorInfoUpload")
    @ResponseBody
    public String doctorInfoUpload(MultipartFile file){
        String dic = "/upload";
        String basePath = this.servletContext.getRealPath(dic);
        String fileName = UploadUtil.upload(file, basePath);
        return dic+"/"+fileName;
    }
}

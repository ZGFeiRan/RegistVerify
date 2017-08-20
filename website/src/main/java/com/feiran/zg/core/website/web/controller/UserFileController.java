package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.domain.SystemDictionaryItem;
import com.feiran.zg.core.base.domain.UserFile;
import com.feiran.zg.core.base.service.IDoctorInfoService;
import com.feiran.zg.core.base.service.ISystemDictionaryService;
import com.feiran.zg.core.base.service.IUserFileService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.website.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 前台用户风控材料控制器
 * Created by zhangguangfei on 2017/2/4.
 */
@Controller
public class UserFileController extends BaseController {

    @Autowired
    private IUserFileService userFileService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @Autowired
    private IDoctorInfoService doctorInfoService;

    @RequireLogin("风控材料认证 ")
    @RequestMapping("userFile")
    public String userFile(Model model, HttpSession session){
        List<UserFile> userFiles = this.userFileService.listUserFilesByHasSelectType(false);
        if(userFiles.size()>0){// 如果有尚未选择类型的风控材料,则跳转到"userFiles_commit"页面
            model.addAttribute("userFiles",userFiles);
            List<SystemDictionaryItem> fileTypes = this.systemDictionaryService.loadBySn("userFileType");
            model.addAttribute("fileTypes",fileTypes);

            return "userFiles_commit";
        }else {// 如果所有风控材料都已经选择了类型,则跳转到"userFiles"页面
            userFiles = this.userFileService.listUserFilesByHasSelectType(true);
            model.addAttribute("userFiles",userFiles);
            model.addAttribute("sessionId",session.getId());
            return "userFiles";
        }
    }

    @RequestMapping("userFileUpload")
    @ResponseBody
    public String userFileUpload(MultipartFile file){
        try {
            String realPath = this.servletContext.getRealPath("/upload");
            String fileName = UploadUtil.upload(file, realPath, doctorInfoService);
            fileName = "/upload/"+fileName;
            this.userFileService.apply(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 给用户上传的风控资料选择分类
     * @param id
     * @param fileType
     * @return
     */
    @RequestMapping("userFile_selectType")
    @ResponseBody
    public JsonResult userFileSelectType(Long[] id, Long[] fileType){
        JsonResult jsonResult = new JsonResult();
        try {
            this.userFileService.chooseTypeFiles(id,fileType);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
}

package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.service.IUserFileService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.UserFileQueryObject;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台风控材料审核控制器
 * Created by zhangguangfei on 2017/2/8.
 */
@Controller
public class UserFileController {
    @Autowired
    private IUserFileService userFileService;

    /**
     * 获取后台风控材料审核列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("userFileAuth")
    public String userFileAuth(@ModelAttribute("qo")UserFileQueryObject qo, Model model){
        PageResult pageResult = this.userFileService.queryForPageResult(qo);
        model.addAttribute("pageResult",pageResult);
        return "userFileAuth/list";
    }

    /**
     * 风控材料审核
     */
    @RequestMapping("userFile_audit")
    @ResponseBody
    public JsonResult userFileAudit(Long id, String remark, int score, int state){
        JsonResult jsonResult = new JsonResult();
        try {
            this.userFileService.audit(id,remark,score,state);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}

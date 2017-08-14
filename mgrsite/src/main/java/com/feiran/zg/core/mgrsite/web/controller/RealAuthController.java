package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.RealAuthQueryObject;
import com.feiran.zg.core.base.service.IRealAuthService;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台的实名认证审核
 * Created by zhangguangfei on 2017/2/3.
 */
@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;

    @RequireLogin("后台实名认证审核")
    @RequestMapping("realAuth")
    private String realAuth(@ModelAttribute("qo")RealAuthQueryObject qo, Model model){
        PageResult pageResult = this.realAuthService.queryForPageResult(qo);
        model.addAttribute("pageResult",pageResult);
        return "realAuth/list";
    }

    /**
     * 实名认证审核
     */
    @RequestMapping("realAuth_audit")
    @ResponseBody
    public JsonResult realAuthAudit(Long id,String remark,int state){
        JsonResult jsonResult = new JsonResult();

        try {
            this.realAuthService.audit(id,remark,state);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }

        return jsonResult;
    }
}

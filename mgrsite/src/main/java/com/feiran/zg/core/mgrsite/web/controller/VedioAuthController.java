package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.query.VedioAuthQueryObject;
import com.feiran.zg.core.base.service.ILoginInfoService;
import com.feiran.zg.core.base.service.IVedioAuthService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.base.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 后台视频认证的Controller
 * Created by zhangguangfei on 2017/2/4.
 */
@Controller
public class VedioAuthController {
    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IVedioAuthService vedioAuthService;

    /**
     * 查询视频认证的数据列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/vedioAuth")
    public String vedioAuthList(@ModelAttribute("qo")VedioAuthQueryObject qo, Model model){
        PageResult pageResult = this.vedioAuthService.queryForPageResult(qo);
        model.addAttribute("pageResult",pageResult);

        return "vedioAuth/list";
    }

    /**
     * 添加视频认证的审核
     * @param loginInfoValue 申请人的id值
     * @param remark
     * @param state
     * @return
     */
    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public JsonResult vedioAuthAudit(Long loginInfoValue, String remark, int state){
        JsonResult jsonResult = new JsonResult();
        try {
            this.vedioAuthService.audit(loginInfoValue,remark,state);// loginInfoValue指的是申请人的id值
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

    /**
     * 自动补全
     */
    @RequestMapping("vedioAuth_autocomplate")
    @ResponseBody
    public List<Map<String ,Object>> vedioAuthAutoComplate(String keyWord){
        return this.loginInfoService.autoComplate(keyWord);
    }
}

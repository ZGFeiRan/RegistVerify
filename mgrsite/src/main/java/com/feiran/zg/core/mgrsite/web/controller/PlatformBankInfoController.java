package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.business.domain.PlatformBankInfo;
import com.feiran.zg.core.business.query.PlatformBankInfoQueryObject;
import com.feiran.zg.core.business.service.IPlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统平台账户管理
 * Created by zhangguangfei on 2017/2/10.
 */
@Controller
public class PlatformBankInfoController {
    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @RequestMapping("companyBank_list")
    public String companyBankList(@ModelAttribute("qo")PlatformBankInfoQueryObject qo, Model model){
        PageResult pageResult = this.platformBankInfoService.queryForPageResult(qo);
        model.addAttribute("pageResult",pageResult);
        return "platformbankinfo/list";
    }

    /**
     * 添加/编辑
     */
    @RequestMapping("companyBank_update")
    @ResponseBody
    public JsonResult companyBankUpdate(PlatformBankInfo bankInfo,Model model){
        JsonResult jsonResult = new JsonResult();
        try {
            this.platformBankInfoService.saveOrUpdate(bankInfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}

package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.business.query.MoneyWithdrawQueryObject;
import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.business.service.IMoneyWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台提现审核
 * Created by zhangguangfei on 2017/2/13.
 */
@Controller
public class MoneyWithdrawController {
    @Autowired
    private IMoneyWithdrawService moneyWithdrawService;

    @RequireLogin("体现审核列表")
    @RequestMapping("moneyWithdraw")
    public String moneyWithdrawList(@ModelAttribute("qo")MoneyWithdrawQueryObject qo, Model model){
        PageResult pageResult = this.moneyWithdrawService.queryForPageResult(qo);
        model.addAttribute("pageResult",pageResult);
        return "moneywithdraw/list";
    }

    @RequestMapping("moneyWithdraw_audit")
    @RequireLogin("后台提现审核")
    @ResponseBody
    public JsonResult moneyWithdrawAudit(Long id,String remark,int state){
        JsonResult jsonResult = new JsonResult();
        this.moneyWithdrawService.audit(id,remark,state);
        return jsonResult;
    }

}



















package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.business.domain.UserBankInfo;
import com.feiran.zg.core.business.service.IMoneyWithdrawService;
import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.business.service.IUserBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 体现相关的Controller
 * Created by zhangguangfei on 2017/2/12.
 */
@Controller
public class MoneyWithdrawController {
    @Autowired
    private IMoneyWithdrawService moneyWithdrawService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserBankInfoService userBankInfoService;

    /**
     * 跳转到体现界面
     */
    @RequireLogin("跳转到提现界面")
    @RequestMapping("moneyWithdraw")
    public String moneyWithdraw(Model model){
        // 获取当前登录的用户
        UserInfo userInfo = this.userInfoService.getCurrent();

        try {
            // 获取当前用户的在本平台的账户
            Account account = this.accountService.getCurrent();
            model.addAttribute("account",account);

            // 判断当前用户是否已经有一个提现申请在审批流程当中
            model.addAttribute("haveProcessing",userInfo.getHasMoneyWithdrawInProcess());

            // 得到当前用户在本平台上绑定的银行卡(本平台规定,一个账户只能绑定一张银行卡,所以可以直接获取当前的银行卡)
            UserBankInfo bankInfo = this.userBankInfoService.getCurrent();
            model.addAttribute("bankInfo",bankInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "moneyWithdraw_apply";
    }

    @RequireLogin("用户提现申请")
    @RequestMapping("moneyWithdraw_apply")
    @ResponseBody
    public JsonResult moneyWithdrawApply(BigDecimal moneyAmount) {
        JsonResult jsonResult = new JsonResult();
        try {
            this.moneyWithdrawService.apply(moneyAmount);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}

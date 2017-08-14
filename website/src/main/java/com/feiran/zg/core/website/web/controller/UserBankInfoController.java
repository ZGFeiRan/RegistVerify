package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.domain.BaseAuditDomain;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.business.domain.UserBankInfo;
import com.feiran.zg.core.business.service.IUserBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户绑定银行卡的Controller
 * Created by zhangguangfei on 2017/2/12.
 */
@Controller
public class UserBankInfoController extends BaseAuditDomain {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserBankInfoService userBankInfoService;

    /**
     * 到想到绑定银行卡页面
     * @return
     */
    @RequireLogin("绑定银行卡")
    @RequestMapping("bankInfo")
    public String bankInfo(Model model){
        UserInfo userInfo = this.userInfoService.getCurrent();
        if (userInfo.getIsBindBank()){
            UserBankInfo bankInfo = this.userBankInfoService.getUserBankInfoByUserId(userInfo.getId());
            model.addAttribute("bankInfo",bankInfo);
            return "bankInfo_result";
        }else {
            model.addAttribute("userInfo", userInfo);
            return "bankInfo";
        }
    }

    @RequireLogin("绑定银行卡")
    @RequestMapping("bankInfo_save")
    public String bindBankInfoSave(UserBankInfo bankInfo){
        try {
            this.userBankInfoService.bindUserBankInfo(bankInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:bankInfo.do";
    }
}




















package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.service.ISystemDictionaryService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.domain.SystemDictionaryItem;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.AccountFlowQueryObject;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/1/13.
 * 个人中心控制器
 */
@Controller
public class PersonalController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @Autowired
    private IAccountFlowService accountFlowService;

    /**
     * 个人中心首页
     */
    @RequestMapping("/personal")
    @RequireLogin("个人中心首页")
    public String personal(Model model){
        model.addAttribute("account", accountService.getCurrent());
        model.addAttribute("userInfo",userInfoService.getCurrent());
        return "personal";
    }

    /**
     * 校验手机验证码,校验通过即可绑定手机号
     */
    @RequestMapping("/bindPhone")
    @ResponseBody
    @RequireLogin("用户绑定手机号")
    public JsonResult bindPhone(String phoneNumber,String verifyCode){
        JsonResult jsonResult = new JsonResult();

        try {
            this.userInfoService.bindPhone(phoneNumber,verifyCode);
            jsonResult.setMsg("您为账号绑定的手机号: "+phoneNumber+" 已经绑定成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
            return jsonResult;
    }

    /***
     * 验证邮件,绑定邮箱
     * @param uuid
     * @param model
     * @return
     */
    @RequestMapping("/bindEmail")
    public String bindEmail(String uuid,Model model){
        try {
            this.userInfoService.bindEmail(uuid);
            model.addAttribute("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("success",false);
            model.addAttribute("msg",e.getMessage());
        }
        return "checkmail_result";
    }

    /**
     * 查看个人资料时,回显用户基本资料
     */
    @RequireLogin("查看个人资料时,回显用户基本资料")
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){
        UserInfo userInfo = this.userInfoService.getCurrent();
        model.addAttribute("userInfo",userInfo);

        List<SystemDictionaryItem> educationBackgrounds = this.systemDictionaryService.loadBySn("educationBackground");
        model.addAttribute("educationBackgrounds",educationBackgrounds);

        List<SystemDictionaryItem> incomeGrades = this.systemDictionaryService.loadBySn("incomeGrade");
        model.addAttribute("incomeGrades",incomeGrades);

        List<SystemDictionaryItem> marriages = this.systemDictionaryService.loadBySn("marriage");
        model.addAttribute("marriages",marriages);

        List<SystemDictionaryItem> kidCounts = this.systemDictionaryService.loadBySn("kidCount");
        model.addAttribute("kidCounts",kidCounts);

        List<SystemDictionaryItem> houseConditions = this.systemDictionaryService.loadBySn("houseCondition");
        model.addAttribute("houseConditions",houseConditions);

        return "userInfo";
    }

    /**
     * 保存/修改用户信息
     */
    @RequireLogin("保存/修改用户信息")
    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JsonResult basicInfoSave(UserInfo userInfo){
        JsonResult jsonResult = new JsonResult();

        try {
            this.userInfoService.saveOrUpdateUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

    /**
     * 流水列表
     */
    @RequestMapping("accountFlow_list")
    @RequireLogin("查询流水列表")
    public String accountFlowList(@ModelAttribute("qo")AccountFlowQueryObject qo, Model model){
        qo.setLoginInfoId(UserContext.getCurrent().getId());// 用户只能查询自己的流水信息
        PageResult pageResult = this.accountFlowService.queryForPageResult(qo);
        model.addAttribute("pageResult",pageResult);
        model.addAttribute("qo",qo);
        return "accountFlow_list";
    }
}
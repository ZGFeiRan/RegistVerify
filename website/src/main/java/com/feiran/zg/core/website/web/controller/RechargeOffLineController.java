package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.domain.PlatformBankInfo;
import com.feiran.zg.core.business.domain.RechargeOffLine;
import com.feiran.zg.core.business.query.RechargeOffLineQueryObject;
import com.feiran.zg.core.business.service.IPlatformBankInfoService;
import com.feiran.zg.core.business.service.IRechargeOffLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 前台线下重置控制器
 * Created by zhangguangfei on 2017/2/10.
 */
@Controller
public class RechargeOffLineController {
    @Autowired
    private IPlatformBankInfoService platformBankInfoService;
    @Autowired
    private IRechargeOffLineService rechargeOffLineService;

    /**
     * 导向到线下充值界面
     * @return
     */
    @RequestMapping("recharge")
    public String recharge(Model model){
        //查询出平台提供用户往平台上充值的所有银行卡
        List<PlatformBankInfo> banks = this.platformBankInfoService.queryForListData();
        model.addAttribute("banks",banks);
        return "recharge";
    }

    /**
     * 线下充值申请
     */
    @RequestMapping("recharge_save")
    @ResponseBody
    public JsonResult rechargeApply(RechargeOffLine ro){
        JsonResult jsonResult = new JsonResult();

        try {
            this.rechargeOffLineService.apply(ro);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

    @RequireLogin("查询线下充值单列表")
    @RequestMapping("recharge_list")
    public String rechargeOffLineList(@ModelAttribute("qo")RechargeOffLineQueryObject qo, Model model){
        PageResult pageResult = null;
        try {
            qo.setApplierId(UserContext.getCurrent().getId());
            pageResult = this.rechargeOffLineService.queryPageResult(qo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("pageResult",pageResult);
        return "recharge_list";
    }
}

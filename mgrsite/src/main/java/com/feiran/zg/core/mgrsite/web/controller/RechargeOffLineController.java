package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.business.domain.PlatformBankInfo;
import com.feiran.zg.core.business.service.IPlatformBankInfoService;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.business.query.RechargeOffLineQueryObject;
import com.feiran.zg.core.business.service.IRechargeOffLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 后台线下充值审核
 * Created by zhangguangfei on 2017/2/10.
 */
@Controller
public class RechargeOffLineController {

    @Autowired
    private IRechargeOffLineService rechargeOffLineService;
    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    /**
     * 后台查询所有提交的线下充值单
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("rechargeOffline")
    public String rechargeOffline(@ModelAttribute("qo")RechargeOffLineQueryObject qo, Model model){
        PageResult pageResult = null;
        List<PlatformBankInfo> banks = null;
        try {
            pageResult = this.rechargeOffLineService.queryPageResult(qo);
            banks = this.platformBankInfoService.queryForListData();
            model.addAttribute("pageResult",pageResult);
            model.addAttribute("banks",banks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "rechargeoffline/list";
    }

    /**
     * 后台审核线下充值单
     */
    @RequestMapping("rechargeOffline_audit")
    @ResponseBody
    public JsonResult rechargeOfflineAudit(Long id,String remark,int state){
        JsonResult jsonResult = new JsonResult();
        try {
            this.rechargeOffLineService.audit(id,remark,state);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

}

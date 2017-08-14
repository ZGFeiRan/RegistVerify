package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.business.service.IBidRequestService;
import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.query.PaymentScheduleQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 还款的Controller
 * Created by zhangguangfei on 2017/2/13.
 */
@Controller
public class ReturnMoneyController {
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IAccountService accountService;

    @RequireLogin("导向")
    @RequestMapping("borrowBidReturn_list")
    public String returnMoneyList(@ModelAttribute("qo")PaymentScheduleQueryObject qo, Model model){
        try {
            qo.setLoginInfoId(UserContext.getCurrent().getId());// 设置查询哪个用户的还款列表,因为用户只能查询自己的还款列表
            PageResult pageResult = this.bidRequestService.queryForPaymentSchedulePageResult(qo);
            model.addAttribute("pageResult",pageResult);

            Account account = this.accountService.getCurrent();
            model.addAttribute("account",account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "returnmoney_list";
    }

    @RequireLogin("借款人还款")
    @RequestMapping("returnMoney")
    @ResponseBody
    public JsonResult returnMoney(Long id){
        JsonResult jsonResult = new JsonResult();
        try {
            this.bidRequestService.returnMoney(id);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

}

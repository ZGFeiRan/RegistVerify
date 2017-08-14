package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.business.query.BidRequestQueryObject;
import com.feiran.zg.core.business.service.IBidRequestService;
import com.feiran.zg.core.base.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台投资控制器
 * Created by zhangguangfei on 2017/2/14.
 */
@Controller
public class InvestController {
    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 投资列表外边的框架
     * @return
     */
    @RequestMapping("invest")
    public String investForm(){

        return "invest";
    }

    /**
     * 投资列表明细
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("invest_list")
    public String investList(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        try {
            PageResult pageResult = this.bidRequestService.queryForPageResult(qo);
            model.addAttribute("pageResult",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "invest_list";
    }
}

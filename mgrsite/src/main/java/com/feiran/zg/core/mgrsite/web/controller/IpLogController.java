package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.IpLogQueryObject;
import com.feiran.zg.core.base.service.IIpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台登陆日志查询控制器
 * Created by zhangguangfei on 2017/1/15.
 */
@Controller
public class IpLogController {
    @Autowired
    private IIpLogService iIpLogService;

    @RequestMapping("ipLog")
    public String ipLogListData(@ModelAttribute("qo")IpLogQueryObject qo, Model model){
        PageResult pageResult = iIpLogService.queryForPageResult(qo);
        model.addAttribute("pageResult",pageResult);
        return "ipLog/list";
    }
}

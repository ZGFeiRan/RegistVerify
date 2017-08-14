package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.IpLogQueryObject;
import com.feiran.zg.core.base.service.IIpLogService;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 查看登陆日志
 * Created by zhangguangfei on 2017/1/14.
 */
@Controller
public class IpLogController {
    @Autowired
    private IIpLogService iIpLogService;

    /***
     * 前台登录日志查询控制器
     * @return
     */
    @RequireLogin("查询登录日志")
    @RequestMapping("ipLog")
    private String ipLogForm(){

        return "iplog";
    }

    @RequireLogin("查询登录日志数据列表")
    @RequestMapping("ipLog_list")
    private String ipLogList(@ModelAttribute("qo") IpLogQueryObject qo, Model model){
        try {
            // 设置当前登录用户的用户名,用户从数据库查询登录日志时的条件参数,限制当前登录的用户只能查看到自己的登录信息。
            qo.setUserName(UserContext.getCurrent().getUserName());
            PageResult pageResult = iIpLogService.queryForPageResult(qo);
            model.addAttribute("pageResult",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "iplog_list";
    }
}

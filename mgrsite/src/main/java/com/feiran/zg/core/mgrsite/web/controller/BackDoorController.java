package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.service.IBackDoorService;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后门操作Controller
 * Created by zhangguangfei on 2017/2/15.
 */
@Controller
public class BackDoorController {
    @Autowired
    private IBackDoorService backDoorService;

    /**
     * 初始化账户防篡改校验码
     * @return
     */
    @RequestMapping("initActionVerify")
    @ResponseBody
    public JsonResult initActionVerify(){
        JsonResult jsonResult = new JsonResult();
        try {
            backDoorService.updateAccountVerify();
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}

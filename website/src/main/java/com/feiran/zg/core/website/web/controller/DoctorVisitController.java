package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.service.IDoctorInfoService;
import com.feiran.zg.core.base.service.IDoctorVisitService;
import com.feiran.zg.core.base.service.IRealAuthService;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by FeiRan_ZG on 2017/8/16.
 *
 * 医生发布坐诊信息
 */
@Controller
public class DoctorVisitController {
    @Autowired
    private IDoctorVisitService doctorVisitService;
    @Autowired
    private IDoctorInfoService doctorInfoService;
    @Autowired
    private IRealAuthService realAuthService;

    /***
     * 用户申请借款
     * @param model
     * @return
     */
    @RequestMapping("doctorVisit")
    public String borrowIndex(Model model){
        if (UserContext.getCurrent()!=null){

            return "doctorVisit";
        }else {
            // 没有登录,就直接导向到borrow.html静态页面
            return "redirect:login.html";
        }
    }

    /**
     * 医生发布坐诊信息的时候，先判断该医生是否已经通过了认证，如果通过了怎跳转到发布坐诊信息页面，否则跳转到提醒用户为进行认证、认证处于审核中或审核被拒绝。
     * @return
     */
    @RequestMapping("doctorVisitApply")
    public String doctorVisitApply(Model model){
        // 获取当前用户
        DoctorInfo current = this.doctorInfoService.getCurrent();

        // 判断当前用户是否可以发布坐诊信息，如果可以则转发到发布坐诊信息的页面
        if (this.doctorVisitService.canApply(current)){
            model.addAttribute("doctorInfo",doctorInfoService.getCurrent());
            return "redirect:/doctorVisit.do";
        }else {
            //如果用户尚未认证、认证处于审核过程中 或 认证被拒绝的时候，跳转掏提醒页面
            RealAuth realAuth = this.realAuthService.getById(current.getRealAuthId());
            model.addAttribute("realAuth",realAuth);
            model.addAttribute("doctorInfo",current);
            return "doctor_visit_apply_result";
        }
    }

    @RequestMapping("doctorVisitHistory")
    public String doctorVisitHistory(){

        return "doctorVisitHistory";
    }
}

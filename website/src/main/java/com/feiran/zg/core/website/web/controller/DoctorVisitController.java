package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.LoginInfo;
import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.domain.VisitInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.VisitInfoQueryObject;
import com.feiran.zg.core.base.service.IAreaService;
import com.feiran.zg.core.base.service.IDoctorInfoService;
import com.feiran.zg.core.base.service.IDoctorVisitService;
import com.feiran.zg.core.base.service.IRealAuthService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private IAreaService areaService;

    /***
     * 保存医生要发布的坐诊信息
     * @param
     * @return
     */
    @RequireLogin("插入发布的坐诊信息")
    @RequestMapping("/doctorVisitInfo_insert")
    @ResponseBody
    public JsonResult doctorVisitInfoInsert(VisitInfo visitInfo){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        if(visitInfo.getBeginDate()==null || "".equals(visitInfo.getBeginDate())){
            jsonResult.setMsg("请选择开始日期");
            return jsonResult;
        }

        if(visitInfo.getEndDate()==null || "".equals(visitInfo.getEndDate())){
            jsonResult.setMsg("请选择结束时间");
            return jsonResult;
        }

        if(visitInfo.getTown()==null || "".equals(visitInfo.getTown())){
            jsonResult.setMsg("请选择出诊场所");
            return jsonResult;
        }

        if(visitInfo.getToplimit()==null || "".equals(visitInfo.getToplimit())){
            jsonResult.setMsg("请输入就诊人数上限");
            return jsonResult;
        }

        int number = doctorVisitService.insertVisitInfo(visitInfo);
        if (number>0){
            jsonResult.setSuccess(true);
            jsonResult.setMsg("");
        }
        return jsonResult;
    }

     /***
     *
     * @param model
     * @return
     */
     @RequireLogin("查询发布的坐诊历史信息")
    @RequestMapping("visitInfoHistory")
    public String doctorVisitHistory(Model model){
//        LoginInfo loginInfoCurrent = UserContext.getCurrent();
//        List<VisitInfo> visitInfoList = doctorVisitService.getDoctorVisitInfosByDoctorId(loginInfoCurrent.getId());
//        if (visitInfoList.size()<=0){
//            return "doctor_visit_info_result";
//        }
//        model.addAttribute("visitInfoList",visitInfoList);
        return "visitInfoHistory";
    }

    @RequireLogin("查询登录日志数据列表")
    @RequestMapping("visitInfoHistory_list")
    private String visitInfoHistoryList(@ModelAttribute("qo") VisitInfoQueryObject qo, Model model){
        try {
            // 设置当前登录用户的id,用户从数据库查询发布的坐镇历史记录时的条件参数,限制当前登录的用户只能查看到自己所发布的坐诊历史记录。
            qo.setPublisherId(UserContext.getCurrent().getId());
            PageResult pageResult = doctorVisitService.queryForPageResult(qo);
            model.addAttribute("pageResult",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "visitInfoHistory_list";
    }



    /**
     * 医生发布坐诊信息的时候，先判断该医生是否已经通过了认证，如果通过了怎跳转到发布坐诊信息页面，否则跳转到提醒用户为进行认证、认证处于审核中或审核被拒绝。
     * @return
     */
    @RequireLogin("申请发布坐诊信息")
    @RequestMapping("doctorVisitApply")
    public String doctorVisitApply(Model model){
        if (UserContext.getCurrent()!=null){
            // 获取当前用户
            DoctorInfo current = this.doctorInfoService.getCurrent();

            // 判断当前用户是否可以发布坐诊信息，如果可以则转发到发布坐诊信息的页面
            if (this.doctorVisitService.canApply(current)){
                model.addAttribute("doctorInfo",doctorInfoService.getCurrent());
                model.addAttribute("area",this.areaService.selectByPrimaryKey(Long.valueOf("110100")));
//                model.addAttribute("mindBeginDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                model.addAttribute("mindBeginDate",new Date());
                return "doctorVisitInfo";
            }else {
                //如果用户尚未认证、认证处于审核过程中 或 认证被拒绝的时候，跳转掏提醒页面
                RealAuth realAuth = this.realAuthService.getById(current.getRealAuthId());
                model.addAttribute("realAuth",realAuth);
                model.addAttribute("doctorInfo",current);
                return "doctor_visit_apply_result";
            }
        }else {
            // 没有登录,就直接导向到borrow.html静态页面
            return "redirect:login.html";
        }
    }
}

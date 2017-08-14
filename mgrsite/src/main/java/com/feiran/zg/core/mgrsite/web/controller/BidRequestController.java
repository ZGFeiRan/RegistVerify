package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.domain.UserFile;
import com.feiran.zg.core.base.service.IRealAuthService;
import com.feiran.zg.core.base.service.IUserFileService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.business.domain.BidRequest;
import com.feiran.zg.core.business.domain.BidRequestAuditHistory;
import com.feiran.zg.core.business.query.BidRequestQueryObject;
import com.feiran.zg.core.business.service.IBidRequestService;
import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.UserFileQueryObject;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 后台借款相关Controller
 * Created by zhangguangfei on 2017/2/9.
 */
@Controller
public class BidRequestController {

    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;

    //===================================发表前审核=====================================
    /**
     * 获取 处于发标前审核状态的借款标的列表
     */
    @RequestMapping("bidrequest_publishaudit_list")
    public String bidrequestPublishAuditList(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);// 设置只查询处于发标前待审核状态的。
        PageResult pageResult = this.bidRequestService.queryForPageResult(qo);
        model.addAttribute("pageResult", pageResult);
        return "bidrequest/publish_audit";
    }

    /**
     * 发标前审核操作
     */
    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public JsonResult bidRequestPublishAudit(Long id, String remark, int state) {
        JsonResult jsonResult = new JsonResult();
        this.bidRequestService.publishAudit(id, remark, state);
        return jsonResult;
    }

    /**
     * 查看所有标的相信信息
     */
    @RequestMapping("borrow_info.do")
    public String borrowInfoDetail(Long id, Model model) {
        // 根据传入的id值,获取相应的BidRequest对象
        BidRequest bidRequest = this.bidRequestService.getById(id);
        if (bidRequest == null) {
            throw new RuntimeException("您要查询的标不存在");
        }
        model.addAttribute("bidRequest",bidRequest);

        // 获取当前这个借款标的申请人(即借款人)
        UserInfo userInfo = this.userInfoService.getById(bidRequest.getCreateUser().getId());
        model.addAttribute("userInfo",userInfo);

        // 获取当前这个借款标的所有审核历史对象
        List<BidRequestAuditHistory> audits = this.bidRequestService.listAuditHistoryByBidRequestId(bidRequest.getId());
        model.addAttribute("audits",audits);

        // 获取当前标的申请者的实名认证信息
        RealAuth realAuth = this.realAuthService.getById(userInfo.getRealAuthId());
        model.addAttribute("realAuth",realAuth);

        // 列出借款人相关的所有风控材料(只需要查询出该借款人审核通过的风控材料即可)
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setState(UserFile.STATE_AUDIT);// 只查询通过审核的风控材料
        qo.setApplierId(userInfo.getId());
        qo.setPageSize(-1);// 因为在mapper.xml文件中配置了只有pageSize>0才会分页插叙,此处设置pageSize=0,就是不分页查询
        List<UserFile> userFiles = this.userFileService.queryForListData(qo);
        model.addAttribute("userFiles",userFiles);
        return "bidrequest/borrow_info";
    }

    //===================================满标一审=====================================
    /**
     * 满标一审管理列表
     */
    @RequestMapping("bidrequest_audit1_list")
    public String bidrequestAudit1List(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);// 设置只查询处于满标一审状态的。
        PageResult pageResult = this.bidRequestService.queryForPageResult(qo);
        model.addAttribute("pageResult", pageResult);
        return "bidrequest/audit1";
    }
    /**
     * 对处于满标一审状态的标进行审核操作
     */
    @RequestMapping("bidrequest_audit1")
    @ResponseBody
    public JsonResult bidrequestAudit1(Long id,String remark,int state){
        JsonResult jsonResult = null;
        try {
            jsonResult = new JsonResult();
            this.bidRequestService.fullAudit1(id, remark, state);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

    //===================================满标二审=====================================
    /**
     * 满标二审管理列表
     */
    @RequestMapping("bidrequest_audit2_list")
    public String bidrequestAudit2List(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);// 设置只查询处于满标二审状态的。
        PageResult pageResult = this.bidRequestService.queryForPageResult(qo);
        model.addAttribute("pageResult", pageResult);
        return "bidrequest/audit2";
    }

    /**
     * 对处于满标二审状态的标进行审核操作
     */
    @RequestMapping("bidrequest_audit2")
    @ResponseBody
    public JsonResult bidrequestAudit2(Long id,String remark,int state){
        JsonResult jsonResult = null;
        try {
            jsonResult = new JsonResult();
            this.bidRequestService.fullAudit2(id, remark, state);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}

package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.domain.UserFile;
import com.feiran.zg.core.base.service.IUserFileService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.business.domain.BidRequest;
import com.feiran.zg.core.business.service.IBidRequestService;
import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.query.UserFileQueryObject;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.service.IRealAuthService;
import com.feiran.zg.core.base.utils.JsonResult;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangguangfei on 2017/1/18.
 */
@Controller
public class BorrowController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;

    /***
     * 用户申请借款
     * @param model
     * @return
     */
    @RequestMapping("borrow")
    public String borrowIndex(Model model){
        if (UserContext.getCurrent()!=null){
            model.addAttribute("account",this.accountService.getCurrent());// 把当前登录用户的账户对象保存到model中
            model.addAttribute("userInfo",this.userInfoService.getCurrent());// 把当前登录用户的用户对象保存到model中
            model.addAttribute("creditBorrowScore", BidConst.CREDIT_BORROW_SCORE);// 把借款时最低风控分数值保存到model中
            // 当用户登录的话,跳往借款动态页面
            return "borrow";
        }else {
            // 没有登录,就直接导向到borrow.html静态页面
            return "redirect:borrow.html";
        }
    }

    /**
     * 跳转到申请借款页面
     * @return
     */
    @RequestMapping("borrowInfo")
    public String borrowInfo(Model model){
        // 获取当前用户
        UserInfo userInfo = this.userInfoService.getCurrent();
        // 判断当前用户是否可以申请借款
        if (this.bidRequestService.canApply(userInfo)){
            // 判断当前用户有没有别的借款尚在申请流程中。
            if (!userInfo.getIsBidRequestInProcess()){// 如果没有就添加属性到model中,并跳转到borrow_apply页面,
                model.addAttribute("minBidRequestAmount",BidConst.SMALLEST_BIDREQUEST_AMOUNT);
                model.addAttribute("minBidAmount",BidConst.SMALLEST_BID_AMOUNT);
                model.addAttribute("account",this.accountService.getCurrent());
                return "borrow_apply";
            }else {// 如果有就返回到borrow_apply_result页面
                return "borrow_apply_result";
            }
        }else {
            return "redirect:/borrow.do";
        }
    }

    /**
     * 申请借款流程
     */
    @RequestMapping("borrow_apply")
    public String borrowApply(BidRequest bidRequest){
        this.bidRequestService.apply(bidRequest);
        return "redirect:/borrow.do";
    }

    /**
     * 查看借款的详细信息
     */
    @RequestMapping("borrow_info")
    public String borrowInfoDetail(Long id,Model model){
        // 根据传入的id值,获取相应的BidRequest对象
        BidRequest bidRequest = this.bidRequestService.getById(id);
        if (bidRequest == null) {
            throw new RuntimeException("您要查询的标不存在");
        }
        model.addAttribute("bidRequest",bidRequest);

        // 获取当前这个借款标的申请人(即借款人)
        UserInfo userInfo = this.userInfoService.getById(bidRequest.getCreateUser().getId());
        model.addAttribute("userInfo",userInfo);

        // 获取当前标的申请者的实名认证信息
        RealAuth realAuth = this.realAuthService.getById(userInfo.getRealAuthId());
        model.addAttribute("realAuth",realAuth);

        // 列出借款人相关的所有风控材料(只需要查询出该借款人审核通过的风控材料即可)
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setState(UserFile.STATE_AUDIT);// 只查询审核通过的风控材料
        qo.setApplierId(userInfo.getId());
        qo.setPageSize(-1);// 因为在mapper.xml文件中配置了只有pageSize>0才会分页查询,此处设置pageSize<0,就是不分页查询
        List<UserFile> userFiles = this.userFileService.queryForListData(qo);
        model.addAttribute("userFiles",userFiles);

        model.addAttribute("self",false);// 默认用户查看的这个借款标不是他自己投的

        if (UserContext.getCurrent()!=null){
            // 如果当前有用户登录,则判断当前登录用户查看的这个标是不是自己投的
            if (UserContext.getCurrent().getId()==userInfo.getId()){
                // 如果是自己投的,则设置self为true
                model.addAttribute("self",true);
            }else {
                // 如果不是自己投的,则试着account
                model.addAttribute("account",this.accountService.getCurrent());
            }
        }
        return "borrow_info";
    }

    @RequestMapping("borrow_bid")
    @ResponseBody
    public JsonResult borrowBid(Long bidRequestId, BigDecimal amount){
        JsonResult jsonResult = new JsonResult();
        try {
            this.bidRequestService.bid(bidRequestId,amount);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}

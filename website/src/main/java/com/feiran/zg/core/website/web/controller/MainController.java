package com.feiran.zg.core.website.web.controller;

import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.business.domain.BidRequest;
import com.feiran.zg.core.business.query.BidRequestQueryObject;
import com.feiran.zg.core.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/9.
 */
@Controller
public class MainController extends BaseController {

    @Autowired
    private IBidRequestService bidRequestService;

    @RequestMapping("index")
    public String index(Model model){
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING,BidConst.BIDREQUEST_STATE_PAYING_BACK,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        qo.setOrderByColumn("b.bidRequestState");
        qo.setOrderByType("ASC");
        qo.setPageSize(5);
        List<BidRequest> bidRequests = this.bidRequestService.queryForListData(qo);
        model.addAttribute("bidRequests",bidRequests);
        return "main";
    }

    @RequestMapping("aboutUs")
    public String aboutUs(){

        return "aboutUs";
    }
}

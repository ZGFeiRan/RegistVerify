package com.feiran.zg.core.business.domain;

import com.alibaba.fastjson.JSONObject;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feiran.zg.core.base.domain.BaseDomain;
import com.feiran.zg.core.base.domain.LoginInfo;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.business.util.DecimalFormatUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import static com.feiran.zg.core.base.utils.BidConst.*;


/**
 * 借款对象
 * Created by zhangguangfei on 2017/2/8.
 */
@Setter@Getter
@Alias("bidRequest")
public class BidRequest extends BaseDomain {
    private int version;// 版本号(用于乐观锁)
    private int returnType;// 还款标类型(默认是:等额本息)
    private int bidRequestType;// 借款标类型(默认是:信用标)
    private int bidRequestState;// 借款标状态(待发布、招标中、已撤销、流标等等)
    private BigDecimal bidRequestAmount= ZERO;// 该借款标的目标借款总金额
    private BigDecimal currentRate= ZERO;// 年化利率
    private BigDecimal minBidAmount= ZERO;// 最小借款金额
    private int monthes2Return;// 还款月数
    private int bidCount = 0;// 该借款标的已投标次数(冗余)
    private BigDecimal totalRewardAmount= ZERO;// 总回报金额(总利息)
    private BigDecimal currentSum = ZERO;// 当前已投标的累计总金额
    private String title;// 借款标的标题
    private String description;// 借款标的描述
    private String note;// 风控审核意见(来自于发表前审核的一间)
    private Date disableDate;// 招标截止日期
    private int disableDays;// 招标天数
    private LoginInfo createUser;// 借款人
    private List<Bid> bids;// 针对该借款标的投标
    private Date applyTime;// 申请时间,(这个时间在我们项目中就是发标前的审核时间)
    private Date publishTime;// 发标时间

    /**
     * 计算当前投标进度,即获取当前已投的金额占总招标金额的百分比
     */
    public BigDecimal getPersent() {

        return currentSum.divide(bidRequestAmount, BidConst.DISPLAY_SCALE,
                RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    /**
     * 计算还需金额,即离满标还差多少金额
     *
     * @return
     */
    public BigDecimal getRemainAmount() {
        return DecimalFormatUtil.formatBigDecimal(
                bidRequestAmount.subtract(currentSum), BidConst.DISPLAY_SCALE);
    }

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("userName", this.createUser.getUserName());
        json.put("title", title);
        json.put("bidRequestAmount", bidRequestAmount);
        json.put("currentRate", currentRate);
        json.put("monthes2Return", monthes2Return);
        json.put("returnType", getReturnTypeDisplay());
        json.put("totalRewardAmount", totalRewardAmount);
        return JSONObject.toJSONString(json);
    }

    public String getReturnTypeDisplay() {
        return returnType == BidConst.RETURN_TYPE_MONTH_INTEREST ? "按月到期"
                : "等额本息";
    }

    public String getBidRequestStateDisplay() {
        switch (this.bidRequestState) {
            case BIDREQUEST_STATE_PUBLISH_PENDING:
                return "待发布";
            case BIDREQUEST_STATE_BIDDING:
                return "招标中";
            case BIDREQUEST_STATE_UNDO:
                return "已撤销";
            case BIDREQUEST_STATE_BIDDING_OVERDUE:
                return "流标";
            case BIDREQUEST_STATE_APPROVE_PENDING_1:
                return "满标一审";
            case BIDREQUEST_STATE_APPROVE_PENDING_2:
                return "满标二审";
            case BIDREQUEST_STATE_REJECTED:
                return "满标审核被拒";
            case BIDREQUEST_STATE_PAYING_BACK:
                return "还款中";
            case BIDREQUEST_STATE_COMPLETE_PAY_BACK:
                return "完成";
            case BIDREQUEST_STATE_PAY_BACK_OVERDUE:
                return "逾期";
            case BIDREQUEST_STATE_PUBLISH_REFUSE:
                return "发标拒绝";
            default:
                return "";
        }
    }
}

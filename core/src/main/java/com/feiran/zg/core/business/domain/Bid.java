package com.feiran.zg.core.business.domain;

import com.feiran.zg.core.base.domain.BaseDomain;
import com.feiran.zg.core.base.domain.LoginInfo;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次投标对象
 * Created by zhangguangfei on 2017/2/8.
 */
@Setter@Getter
@Alias("bid")
public class Bid extends BaseDomain {
    private BigDecimal actualRate;// 年化利率(等同于bidrequest上的currentRate)
    private BigDecimal availableAmount;// 本次投标金额
    private Long bidRequestId;// 关联借款标的id
    private String bidRequestTitle;// 冗余数据,等同于借款标题
    private LoginInfo bidUser;// 投标人
    private Date bidTime;// 投标时间
    private int bidRequestState;// 投标时,所投的借款标所处的状态,不保存到数据库中,只供查询使用
}

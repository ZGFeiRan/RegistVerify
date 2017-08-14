package com.feiran.zg.core.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.feiran.zg.core.base.domain.BaseAuditDomain;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线下充值单对象
 * Created by zhangguangfei on 2017/2/10.
 */
@Setter@Getter
@Alias("rechargeOffLine")
public class RechargeOffLine extends BaseAuditDomain {
    private PlatformBankInfo bankInfo;
    private String tradeCode;// 交易号
    private BigDecimal amount;// 充值金额

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;// 充值时间

    private String note;// 充值说明

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("userName", this.applier.getUserName());
        json.put("tradeCode", tradeCode);
        json.put("amount", amount);
        json.put("tradeTime", DateFormat.getDateInstance(DateFormat.FULL).format(this.tradeTime));
        return JSONObject.toJSONString(json);
    }
}

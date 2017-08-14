package com.feiran.zg.core.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feiran.zg.core.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户流水对象
 * Created by zhangguangfei on 2017/2/10.
 */
@Setter@Getter
public class AccountFlow extends BaseDomain {
    private Long accountId;// 该账户流水对象所属的账户的id
    private BigDecimal amount;// 本次流水变动的金额

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date vdate;// 发生的日期
    private int accountActionType;// 操作的类型
    private String note;// 说明
    private BigDecimal useableAmoumt;// 本次操作之后的可以用余额
    private BigDecimal freezedAmount;// 本次操作之后的冻结金额
}

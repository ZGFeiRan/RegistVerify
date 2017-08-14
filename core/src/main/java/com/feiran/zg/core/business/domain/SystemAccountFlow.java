package com.feiran.zg.core.business.domain;

import com.feiran.zg.core.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统账户账户流水
 * Created by zhangguangfei on 2017/2/11.
 */
@Setter@Getter
public class SystemAccountFlow extends BaseDomain {
    private Date vDate;// 流水创建时间
    private int accountType;// 系统账户流水类型
    private BigDecimal amount;// 流水相关金额
    private String note;
    private BigDecimal useableAmount;// 流水产生之后系统账户的可用余额;
    private BigDecimal freezedAmount;// 流水产生之后系统账户的冻结余额;
    private Long systemAccountId;// 对应的系统账户的id
}

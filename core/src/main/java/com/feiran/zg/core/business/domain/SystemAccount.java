package com.feiran.zg.core.business.domain;

import com.feiran.zg.core.base.domain.BaseDomain;
import com.feiran.zg.core.base.utils.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 平台账户
 * Created by zhangguangfei on 2017/2/11.
 */
@Setter@Getter
public class SystemAccount extends BaseDomain {
    private int version;// 乐观锁的版本控制字段
    private BigDecimal usableAmount = BidConst.ZERO;// 平台账户剩余金额
    private BigDecimal freezedAmount = BidConst.ZERO;// 平台账户冻结金额
}

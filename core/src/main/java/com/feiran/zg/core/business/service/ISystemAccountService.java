package com.feiran.zg.core.business.service;

import com.feiran.zg.core.business.domain.BidRequest;
import com.feiran.zg.core.business.domain.MoneyWithdraw;
import com.feiran.zg.core.business.domain.PaymentScheduleDetail;
import com.feiran.zg.core.business.domain.SystemAccount;

import java.math.BigDecimal;

/**
 * 平台账户业务操作
 * Created by zhangguangfei on 2017/2/11.
 */
public interface ISystemAccountService {
    // 带有乐观锁操作的更行
    void update(SystemAccount systemAccount);

    // 检查并初始化平台账户
    void initSystemAccount();

    // 系统平台收手续费(即管理费)
    void chargeBorrowFee(BigDecimal borrowChargeFee, BidRequest bidRequest);

    // 获取系统账户
    SystemAccount selectCurrent();

    // 系统张数收取提现手续费
    void chargeMoneyWidthdrawFee(MoneyWithdraw m);

    // 收取利息管理费
    void chargeInterestFee(BigDecimal interestManagerChargeFee, PaymentScheduleDetail detail);
}

package com.feiran.zg.core.business.service;

import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.query.AccountFlowQueryObject;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.business.domain.*;

import java.math.BigDecimal;

/**
 * 处理账户的流水信息
 * Created by zhangguangfei on 2017/2/10.
 */
public interface IAccountFlowService {
    /**
     * 生成一条线下充值的流水记录
     */
    void insertRecgargFlow(Account account, RechargeOffLine ro);

    /**
     * 生成一条投标的流水记录
     */
    void insertBidFlow(Account account, Bid bid);

    /**
     * 提现成功,生成一条投标失败的流水记录
     */
    void cancleBidFlow(Account account, Bid bid);

    /**
     * 生成一条借款成功的流水记录
     */
    void borrowSuccessFlow(Account account, BidRequest bidRequest);

    /**
     * 生成一条借款手续费的流水记录
     */
    void insertBorrowChargeFee(BigDecimal borrowChargeFee, Account account, BidRequest bidRequest);

    /**
     * 生成一条投标成功的流水记录
     */
    void insertBidSuccessFlow(Bid bid, Account bidAccount);

    /**
     * 生成一条提现申请的冻结的流水记录
     */
    void insertMoneyWithdrawFlow(Account account, MoneyWithdraw m);

    /**
     * 提现成功,生成一条提现成功的流水记录
     */
    void insertMoneyWithdrawSuccessFlow(Account account, MoneyWithdraw m);

    /**
     * 提现成功,生成一条提现手续费的流水记录
     */
    void insertMoneyWithdrawChargeFeeFlow(Account account, MoneyWithdraw m);

    /**
     * 取消提现,生成一条取消提现的流水记录
     */
    void insertMoneyWithdrawFaileFlow(Account account, MoneyWithdraw m);

    /**
     * 生成一个某一期还款的流水记录
     */
    void insertReturnMoneyFlow(Account returnAccount, PaymentSchedule schedule);

    /**
     * 投资人收到自己所有某个借款标的某一期还款时,生成一个收款的流水记录
     */
    void insertReceiveMoneyFlow(Account account, PaymentScheduleDetail schedule);


    /**
     * 生成一个利息管理费的流水记录
     */
    void insertInterestManagerChargeFee(Account account, PaymentScheduleDetail detail, BigDecimal interestManagerChargeFee);

    /**
     * 查询账户流水信息
     */
    PageResult queryForPageResult(AccountFlowQueryObject qo);
}

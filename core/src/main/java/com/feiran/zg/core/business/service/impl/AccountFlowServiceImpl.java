package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.AccountFlowQueryObject;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.business.domain.*;
import com.feiran.zg.core.business.mapper.AccountFlowMapper;
import com.feiran.zg.core.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/10.
 */
@Service
public class AccountFlowServiceImpl implements IAccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    /**
     * 生成一条线下充值流水
     * @param account
     * @param ro
     */
    @Override
    public void insertRecgargFlow(Account account, RechargeOffLine ro) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
        flow.setAccountId(account.getId());
        flow.setAmount(ro.getAmount());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setUseableAmoumt(account.getUsableAmount());
        flow.setVdate(new Date());
        flow.setNote("在"+ DateFormat.getDateInstance().format(ro.getTradeTime())+",线下充值成功,充值金额为: "+ro.getAmount());

        this.accountFlowMapper.insert(flow);
    }

    /**
     * 生成一条投标流水
     */
    @Override
    public void insertBidFlow(Account account, Bid bid) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountId(account.getId());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_FREEZED);
        flow.setVdate(new Date());
        flow.setAmount(bid.getAvailableAmount());
        flow.setUseableAmoumt(account.getUsableAmount());
        flow.setNote("投标"+bid.getBidRequestTitle()+"借款,本次投标的冻结金额为:"+bid.getAvailableAmount());

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void cancleBidFlow(Account account, Bid bid) {
        AccountFlow flow = new AccountFlow();
        flow.setUseableAmoumt(account.getUsableAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setAmount(bid.getAvailableAmount());
        flow.setAccountId(account.getId());
        flow.setVdate(new Date());
        flow.setNote("投标"+bid.getBidRequestTitle()+"借款标,投标失败,取消的冻结金额为:"+bid.getAvailableAmount());

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void borrowSuccessFlow(Account account, BidRequest bidRequest) {
        AccountFlow flow = new AccountFlow();
        flow.setVdate(new Date());
        flow.setAccountId(account.getId());
        flow.setAmount(bidRequest.getBidRequestAmount());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL);
        flow.setUseableAmoumt(account.getUsableAmount());
        flow.setNote("借款"+bidRequest.getTitle()+"借款成功,本次借款金额为:"+bidRequest.getBidRequestAmount());

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertBorrowChargeFee(BigDecimal borrowChargeFee, Account account, BidRequest bidRequest) {
        AccountFlow flow = new AccountFlow();
        flow.setUseableAmoumt(account.getUsableAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_CHARGE);
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setAmount(borrowChargeFee);
        flow.setAccountId(account.getId());
        flow.setVdate(new Date());
        flow.setNote("借款"+bidRequest.getTitle()+"借款成功,支付借款手续费:"+borrowChargeFee);

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertBidSuccessFlow(Bid bid, Account bidAccount) {
        AccountFlow flow = new AccountFlow();

        flow.setUseableAmoumt(bidAccount.getUsableAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
        flow.setFreezedAmount(bidAccount.getFreezedAmount());
        flow.setAmount(bid.getAvailableAmount());
        flow.setAccountId(bidAccount.getId());
        flow.setVdate(new Date());
        flow.setNote("投标"+bid.getBidRequestTitle()+"借款表,投标成功,取消的冻结金额为:"+bid.getAvailableAmount());

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertMoneyWithdrawFlow(Account account, MoneyWithdraw m) {
        AccountFlow flow = new AccountFlow();
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setVdate(new Date());
        flow.setAccountId(account.getId());
        flow.setAmount(m.getAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW_FREEZED);
        flow.setUseableAmoumt(account.getUsableAmount());
        flow.setNote("提现申请冻结金额,冻结金额为:"+m.getAmount());

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertMoneyWithdrawSuccessFlow(Account account, MoneyWithdraw m) {
        AccountFlow flow = new AccountFlow();
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setVdate(new Date());
        flow.setAccountId(account.getId());
        flow.setAmount(m.getAmount().subtract(m.getChargeFee()));
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW);
        flow.setUseableAmoumt(account.getUsableAmount());
        flow.setNote("提现成功,减少冻结金额,金额为:"+m.getAmount());

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertMoneyWithdrawChargeFeeFlow(Account account, MoneyWithdraw m) {
        AccountFlow flow = new AccountFlow();
        flow.setVdate(new Date());
        flow.setAccountId(account.getId());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setUseableAmoumt(account.getUsableAmount());

        flow.setNote("提现成功,支付手续费:"+m.getChargeFee());
        flow.setAmount(m.getChargeFee());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertMoneyWithdrawFaileFlow(Account account, MoneyWithdraw m) {
        AccountFlow flow = new AccountFlow();
        flow.setVdate(new Date());
        flow.setAccountId(account.getId());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setUseableAmoumt(account.getUsableAmount());

        flow.setNote("提现取消,取消冻结金额:"+m.getAmount());
        flow.setAmount(m.getAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW_UNFREEZED);

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertReturnMoneyFlow(Account returnAccount, PaymentSchedule schedule) {
        AccountFlow flow = new AccountFlow();
        flow.setVdate(new Date());
        flow.setAccountId(returnAccount.getId());
        flow.setFreezedAmount(returnAccount.getFreezedAmount());
        flow.setUseableAmoumt(returnAccount.getUsableAmount());

        flow.setNote(schedule.getBidRequestTitle()+"这个借款标的第"+schedule.getMonthIndex()+"期还款成功,还款金额:"+schedule.getTotalAmount());
        flow.setAmount(schedule.getTotalAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_RETURN_MONEY);

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertReceiveMoneyFlow(Account account, PaymentScheduleDetail detail) {
        AccountFlow flow = new AccountFlow();
        flow.setVdate(new Date());
        flow.setAccountId(account.getId());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setUseableAmoumt(account.getUsableAmount());

        flow.setNote("收到第"+detail.getMonthIndex()+"期的收款,收款金额:"+detail.getTotalAmount());
        flow.setAmount(detail.getTotalAmount());
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY);

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void insertInterestManagerChargeFee(Account account, PaymentScheduleDetail detail, BigDecimal interestManagerChargeFee) {
        AccountFlow flow = new AccountFlow();
        flow.setVdate(new Date());
        flow.setAccountId(account.getId());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setUseableAmoumt(account.getUsableAmount());

        flow.setNote("收到"+detail.getBidRequestTitle()+"借款标的第"+detail.getMonthIndex()+"期的收款,支付利息管理费:"+interestManagerChargeFee);
        flow.setAmount(interestManagerChargeFee);
        flow.setAccountActionType(BidConst.ACCOUNT_ACTIONTYPE_INTEREST_SHARE);

        this.accountFlowMapper.insert(flow);
    }

    @Override
    public PageResult queryForPageResult(AccountFlowQueryObject qo) {
        int count = this.accountFlowMapper.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<AccountFlow> listData = this.accountFlowMapper.queryForListData(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}

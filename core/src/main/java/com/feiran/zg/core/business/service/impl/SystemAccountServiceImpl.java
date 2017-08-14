package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.business.domain.*;
import com.feiran.zg.core.business.mapper.SystemAccountFlowMapper;
import com.feiran.zg.core.business.mapper.SystemAccountMapper;
import com.feiran.zg.core.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangguangfei on 2017/2/11.
 */
@Service
public class SystemAccountServiceImpl implements ISystemAccountService{
    @Autowired
    private SystemAccountMapper systemAccountMapper;
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;


    @Override
    public void update(SystemAccount systemAccount) {
        int count = this.systemAccountMapper.updateByPrimaryKey(systemAccount);
        if (count<=0){
            throw new RuntimeException("乐观锁失败:SystemAccount");
        }
    }

    @Override
    public void initSystemAccount() {
        SystemAccount systemAccount = this.systemAccountMapper.selectCurrent();
        if (systemAccount==null){
            systemAccount = new SystemAccount();
            this.systemAccountMapper.insert(systemAccount);
        }
    }

    @Override
    public void chargeBorrowFee(BigDecimal borrowChargeFee, BidRequest bidRequest) {
        // 获取系统当前账户
        SystemAccount systemAccount = this.systemAccountMapper.selectCurrent();
        // 修改系统账户余额
        systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(borrowChargeFee));
        // 生成一条系统账户金额变动的明细
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setSystemAccountId(systemAccount.getId());
        flow.setAmount(borrowChargeFee);
        flow.setFreezedAmount(systemAccount.getFreezedAmount());
        flow.setUseableAmount(systemAccount.getUsableAmount());
        flow.setAccountType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);
        flow.setVDate(new Date());

        flow.setNote("借款:"+bidRequest.getTitle()+"成功, 收取借款手续费:"+borrowChargeFee);

        this.systemAccountFlowMapper.insert(flow);

        this.update(systemAccount);
    }

    @Override
    public SystemAccount selectCurrent() {
        return this.systemAccountMapper.selectCurrent();
    }

    @Override
    public void chargeMoneyWidthdrawFee(MoneyWithdraw m) {
        // 得到系统账户
        SystemAccount account = this.systemAccountMapper.selectCurrent();
        // 修改系统账户余额
        account.setUsableAmount(account.getUsableAmount().add(m.getChargeFee()));
        // 生成一条系统账户金额变动的明细
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setSystemAccountId(account.getId());
        flow.setAmount(m.getChargeFee());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setUseableAmount(account.getUsableAmount());
        flow.setAccountType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
        flow.setVDate(new Date());

        flow.setNote(m.getApplier().getUserName()+"提现成功,提现金额为"+m.getAmount()+" 收取提现手续费:"+m.getChargeFee());

        this.systemAccountFlowMapper.insert(flow);

        this.update(account);
    }

    @Override
    public void chargeInterestFee(BigDecimal interestManagerChargeFee, PaymentScheduleDetail detail) {
        // 得到系统账户
        SystemAccount systemAccount = this.systemAccountMapper.selectCurrent();
        // 修改系统账户余额
        systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(interestManagerChargeFee));

        // 生成一条系统账户金额变动的明细
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setSystemAccountId(systemAccount.getId());
        flow.setAmount(interestManagerChargeFee);
        flow.setFreezedAmount(systemAccount.getFreezedAmount());
        flow.setUseableAmount(systemAccount.getUsableAmount());
        flow.setAccountType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE);
        flow.setVDate(new Date());

        flow.setNote("收取"+detail.getBidRequestTitle()+"借款标第"+detail.getMonthIndex()+"期还款的利息管理费,管理费金额为:"+interestManagerChargeFee);

        this.systemAccountFlowMapper.insert(flow);

        this.update(systemAccount);
    }
}

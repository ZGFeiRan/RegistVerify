package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.business.domain.UserBankInfo;
import com.feiran.zg.core.business.query.MoneyWithdrawQueryObject;
import com.feiran.zg.core.business.service.IMoneyWithdrawService;
import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.base.utils.BitStatesUtils;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.domain.MoneyWithdraw;
import com.feiran.zg.core.business.mapper.MoneyWithdrawMapper;
import com.feiran.zg.core.business.service.IAccountFlowService;
import com.feiran.zg.core.business.service.ISystemAccountService;
import com.feiran.zg.core.business.service.IUserBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/12.
 */
@Service
public class MoneyWithdrawServiceImpl implements IMoneyWithdrawService {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserBankInfoService bankInfoService;
    @Autowired
    private MoneyWithdrawMapper moneyWithdrawMapper;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;

    @Override
    public void apply(BigDecimal moneyAmount) {
        // 得到当前用户
        UserInfo userInfo = this.userInfoService.getCurrent();
        Account account = this.accountService.getCurrent();
        // 判断当前用户已经绑定了银行卡,并且没有其他提现申请在审核流程中,并且申请提现的金额小于等于用户账户中的可用余额
        if (userInfo.getIsBindBank()
                && !userInfo.getHasMoneyWithdrawInProcess()
                && moneyAmount.compareTo(account.getUsableAmount()) <= 0
                && moneyAmount.compareTo(BidConst.MIN_WITHDRAW_AMOUNT) >= 0
                ) {
            // 获取当前用户在本平台上绑定的银行卡
            UserBankInfo bankInfo = this.bankInfoService.getCurrent();
            // 创建提现申请对象,然后设置相关属性,并保存
            MoneyWithdraw m = new MoneyWithdraw();
            m.setApplier(UserContext.getCurrent());
            m.setApplyTime(new Date());
            m.setBankName(bankInfo.getBankName());
            m.setBankForkName(bankInfo.getBankForkName());
            m.setAccountName(bankInfo.getAccountName());
            m.setAccountNumber(bankInfo.getAccountNumber());
            m.setAmount(moneyAmount);
            m.setChargeFee(BidConst.MONEY_WITHDRAW_CHARGEFEE);
            m.setState(MoneyWithdraw.STATE_NORMAL);

            this.moneyWithdrawMapper.insert(m);

            // 用户的可用余额减少,冻结余额增加
            account.setFreezedAmount(account.getFreezedAmount().add(moneyAmount));
            account.setUsableAmount(account.getUsableAmount().subtract(moneyAmount));

            // 生成提现申请冻结流水
            this.accountFlowService.insertMoneyWithdrawFlow(account, m);

            // 给用户添加状态码
            userInfo.setState(BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS);
            this.userInfoService.update(userInfo);

            this.accountService.update(account);
        }
    }

    @Override
    public PageResult queryForPageResult(MoneyWithdrawQueryObject qo) {
        int count = this.moneyWithdrawMapper.queryForCount(qo);
        if (count <= 0) {
            return PageResult.empty(qo.getPageSize());
        }

        List<MoneyWithdraw> listData = this.moneyWithdrawMapper.queryForListData(qo);

        return new PageResult(listData, count, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<MoneyWithdraw> queryForListData(MoneyWithdrawQueryObject qo) {
        return this.moneyWithdrawMapper.queryForListData(qo);
    }

    @Override
    public void audit(Long id, String remark, int state) {

        // 获取提现申请对象
        MoneyWithdraw m = this.moneyWithdrawMapper.selectByPrimaryKey(id);
        // 获取申请提现的用户
        UserInfo userInfo = this.userInfoService.getById(m.getApplier().getId());
        // 获取申请提现用户在本平台上的账户
        Account account = this.accountService.getById(userInfo.getId());

        // 判断提现申请对象的状态
        if (m != null && m.getState() == MoneyWithdraw.STATE_NORMAL) {
            // 设置相关属性
            m.setAuditor(UserContext.getCurrent());
            m.setAuditTime(new Date());
            m.setRemark(remark);
            m.setState(state);
            // 判断审核状态,是审核通过,还是审核失败
            if (state == MoneyWithdraw.STATE_AUDIT){
                // 审核通过
                // 1、减少冻结金额,生成一条提现的流水对象
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getAmount().subtract(m.getChargeFee())));
                this.accountFlowService.insertMoneyWithdrawSuccessFlow(account,m);
                // 2、减少冻结金额,生成一条提现手续费的流水对象
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getChargeFee()));
                this.accountFlowService.insertMoneyWithdrawChargeFeeFlow(account,m);
                // 3、系统账户收取提现手续费
                this.systemAccountService.chargeMoneyWidthdrawFee(m);
            }else {
                // 审核失败
                // 1、减少冻结金额,增加可用余额,生成取消提现的流水对象
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getAmount()));
                account.setUsableAmount(account.getUsableAmount().add(m.getAmount()));
                this.accountFlowService.insertMoneyWithdrawFaileFlow(account,m);
            }

            this.moneyWithdrawMapper.updateByPrimaryKey(m);

            this.accountService.update(account);

            // 取消状态码
            userInfo.removeState(BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS);
            this.userInfoService.update(userInfo);

        }
    }
}

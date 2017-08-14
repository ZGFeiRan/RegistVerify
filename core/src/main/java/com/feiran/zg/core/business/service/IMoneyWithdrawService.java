package com.feiran.zg.core.business.service;

import com.feiran.zg.core.business.query.MoneyWithdrawQueryObject;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.business.domain.MoneyWithdraw;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体现相关的Service
 * Created by zhangguangfei on 2017/2/12.
 */
public interface IMoneyWithdrawService {
    /**
     * 用户提现申请
     */
    void apply(BigDecimal moneyAmount);

    /**
     * 高级查询
     */
    PageResult queryForPageResult(MoneyWithdrawQueryObject qo);

    List<MoneyWithdraw> queryForListData(MoneyWithdrawQueryObject qo);

    /**
     * 后台提现审核
     */
    void audit(Long id, String remark,int state);
}

package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.mapper.RechargeOffLineMapper;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.business.domain.RechargeOffLine;
import com.feiran.zg.core.business.query.RechargeOffLineQueryObject;
import com.feiran.zg.core.business.service.IAccountFlowService;
import com.feiran.zg.core.business.service.IRechargeOffLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by zhangguangfei on 2017/2/10.
 */
@Service
public class RechargeOffLineServiceImpl implements IRechargeOffLineService {
    @Autowired
    private RechargeOffLineMapper rechargeOffLineMapperr;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;

//    @Autowired
//    private RechargeOffLineMapper rechargeOffLineMapper;

    /**
     * 线下充值申请
     * @param ro
     */
    @Override
    public void apply(RechargeOffLine ro) {
        // 创建一个新的充值单对象
        RechargeOffLine offLine = new RechargeOffLine();
        // 设置属性
        offLine.setAmount(ro.getAmount());
        offLine.setBankInfo(ro.getBankInfo());
        offLine.setNote(ro.getNote());
        offLine.setTradeCode(ro.getTradeCode());
        offLine.setTradeTime(ro.getTradeTime());
        offLine.setApplier(UserContext.getCurrent());
        offLine.setApplyTime(new Date());
        offLine.setState(RechargeOffLine.STATE_NORMAL);// 设置充值单对象为正常状态,待审核状态

        // 保存对象
        this.rechargeOffLineMapperr.insert(offLine);
    }

    @Override
    public PageResult queryPageResult(RechargeOffLineQueryObject qo) {
        int count = this.rechargeOffLineMapperr.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<RechargeOffLine> listData = this.rechargeOffLineMapperr.queryForListData(qo);

        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    /**
     * 线下充值审核
     * @param id
     * @param remark
     * @param state
     */
    @Override
    public void audit(Long id,String remark,int state) {
        // 根据传进来的查询出线下申请单
        RechargeOffLine ro = this.rechargeOffLineMapperr.selectByPrimaryKey(id);
        if (ro!=null && ro.getState() == RechargeOffLine.STATE_NORMAL){
            // 设置相关属性
            ro.setAuditor(UserContext.getCurrent());
            ro.setAuditTime(new Date());
            ro.setRemark(remark);
            ro.setState(state);
            // 判断是否是审核通过
            if (state == RechargeOffLine.STATE_AUDIT){
                // 得到申请人的账户信息,账户的可用余额增加
                Account account = this.accountService.getById(ro.getApplier().getId());
                account.setUsableAmount(account.getUsableAmount().add(ro.getAmount()));
                // 添加一条充值流水信息
                this.accountFlowService.insertRecgargFlow(account,ro);
                // 更新账户信息
                this.accountService.update(account);
            }

            // 更新
            this.rechargeOffLineMapperr.updateByPrimaryKey(ro);
        }else {
            throw new RuntimeException("您要审核的线下充值单不存在,或不处于待审核状态,请查证");
        }

    }
}

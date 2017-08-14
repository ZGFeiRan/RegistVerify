package com.feiran.zg.core.business.service;

import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.business.domain.RechargeOffLine;
import com.feiran.zg.core.business.query.RechargeOffLineQueryObject;

/**
 * Created by zhangguangfei on 2017/2/10.
 */
public interface IRechargeOffLineService {

    /**
     * 前台用户线下充值申请
     * @param ro
     */
    void apply(RechargeOffLine ro);

    PageResult queryPageResult(RechargeOffLineQueryObject qo);

    /**
     * 后台对前台用户提交的线下充值单进行审核
     */
    void audit(Long id,String remark,int state);
}

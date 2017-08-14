package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.RealAuthQueryObject;

/**
 * 实名认真审核相关服务
 * Created by zhangguangfei on 2017/2/3.
 */
public interface IRealAuthService {
    RealAuth getById(Long id);

    /**
     * 提交实名认证审核
     * @param realAuth
     */
    void apply(RealAuth realAuth);

    PageResult queryForPageResult(RealAuthQueryObject qo);

    void audit(Long id,String remark,int state);
}

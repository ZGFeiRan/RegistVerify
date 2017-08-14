package com.feiran.zg.core.business.service;

import com.feiran.zg.core.business.domain.UserBankInfo;

/**
 * 用户绑定银行卡的Service
 * Created by zhangguangfei on 2017/2/12.
 */
public interface IUserBankInfoService {
    /**
     * 根据用户id值查询这个用户绑定的银行卡信息
     * @param id
     * @return
     */
    UserBankInfo getUserBankInfoByUserId(Long id);

    /**
     * 用户绑定银行卡操作
     * @param bankInfo
     */
    void bindUserBankInfo(UserBankInfo bankInfo);

    /**
     * 获取当前登录用户绑定的银行卡
     * @return
     */
    UserBankInfo getCurrent();
}

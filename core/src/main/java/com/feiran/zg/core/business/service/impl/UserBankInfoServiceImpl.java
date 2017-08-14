package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.business.domain.UserBankInfo;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.BitStatesUtils;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.mapper.UserBankInfoMapper;
import com.feiran.zg.core.business.service.IUserBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangguangfei on 2017/2/12.
 */
@Service
public class UserBankInfoServiceImpl implements IUserBankInfoService {
    @Autowired
    private UserBankInfoMapper userBankInfoMapper;

    @Autowired
    private IUserInfoService userInfoService;


    @Override
    public UserBankInfo getUserBankInfoByUserId(Long id) {
        return this.userBankInfoMapper.selectByUserId(id);
    }

    @Override
    public void bindUserBankInfo(UserBankInfo bankInfo) {
        // 获取当前用户
        UserInfo userInfo = this.userInfoService.getCurrent();
        // 判断当前用户有没有绑定过银行卡
        if (userInfo!=null && !userInfo.getIsBindBank() && userInfo.getIsRealAuth()){
            // 用户没有绑定过银行卡
            // 创建一个用户银行卡对象,并设置相关属性,然后保存
            UserBankInfo bank = new UserBankInfo();
            bank.setAccountName(userInfo.getRealName());
            bank.setAccountNumber(bankInfo.getAccountNumber());
            bank.setBankForkName(bankInfo.getBankForkName());
            bank.setLoginInfo(UserContext.getCurrent());
            bank.setBankName(bankInfo.getBankName());

            this.userBankInfoMapper.insert(bank);

            // 给用户添加"绑定过手机号"的状态码
            userInfo.setState(BitStatesUtils.OP_BIND_BANKINFO);
            this.userInfoService.update(userInfo);
        }else {
            throw new RuntimeException("您已经绑定过银行卡了,请勿重复绑定!如需修改绑定的银行卡,请联系客服");
        }
    }

    @Override
    public UserBankInfo getCurrent() {
        return this.getUserBankInfoByUserId(UserContext.getCurrent().getId());
    }
}

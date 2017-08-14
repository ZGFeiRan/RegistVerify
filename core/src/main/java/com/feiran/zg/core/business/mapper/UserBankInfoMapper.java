package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.UserBankInfo;

public interface UserBankInfoMapper {

    int insert(UserBankInfo record);

    UserBankInfo selectByUserId(Long userId);

}
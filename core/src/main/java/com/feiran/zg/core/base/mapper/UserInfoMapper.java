package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.UserInfo;

public interface UserInfoMapper {

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);


    int updateByPrimaryKey(UserInfo record);
}
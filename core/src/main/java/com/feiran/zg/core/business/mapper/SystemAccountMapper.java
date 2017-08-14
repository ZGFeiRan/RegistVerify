package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.SystemAccount;

public interface SystemAccountMapper {

    int insert(SystemAccount record);

    SystemAccount selectCurrent();

    int updateByPrimaryKey(SystemAccount record);
}
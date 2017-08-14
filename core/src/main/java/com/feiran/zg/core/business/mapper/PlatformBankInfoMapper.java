package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.PlatformBankInfo;
import com.feiran.zg.core.business.query.PlatformBankInfoQueryObject;

import java.util.List;

public interface PlatformBankInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlatformBankInfo record);

    PlatformBankInfo selectByPrimaryKey(Long id);

    List<PlatformBankInfo> selectAll();

    int updateByPrimaryKey(PlatformBankInfo record);

    // 高级查询
    int queryForCount(PlatformBankInfoQueryObject qo);
    List<PlatformBankInfo> queryForListData(PlatformBankInfoQueryObject qo);


}
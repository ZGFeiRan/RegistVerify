package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.query.RealAuthQueryObject;
import com.feiran.zg.core.base.domain.RealAuth;

import java.util.List;

public interface RealAuthMapper {

    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

    // 分页查询相关
    int queryForCount(RealAuthQueryObject qo);

    List<RealAuth> queryForListData(RealAuthQueryObject qo);
}
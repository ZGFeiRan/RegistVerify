package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.VedioAuth;
import com.feiran.zg.core.base.query.VedioAuthQueryObject;

import java.util.List;

public interface VedioAuthMapper {

    int insert(VedioAuth record);

    VedioAuth selectByPrimaryKey(Long id);

    // 高级查询
    int queryForCount(VedioAuthQueryObject qo);

    List<VedioAuth> queryForListData(VedioAuthQueryObject qo);
}
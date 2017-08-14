package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.RechargeOffLine;
import com.feiran.zg.core.business.query.RechargeOffLineQueryObject;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/10.
 */
public interface RechargeOffLineMapper {

    int insert(RechargeOffLine record);

    RechargeOffLine selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeOffLine record);

    // 高级查询
    int queryForCount(RechargeOffLineQueryObject qo);
    List<RechargeOffLine> queryForListData(RechargeOffLineQueryObject qo);

}

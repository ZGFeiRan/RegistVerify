package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.query.IpLogQueryObject;
import com.feiran.zg.core.base.domain.IpLog;

import java.util.List;

public interface IpLogMapper {

    int insert(IpLog record);
    // 高级查询相关
    // 查询符合条件的数据的条数
    int quetryForCount(IpLogQueryObject qo);
    // 查询符合条件的当前页的数据
    List<IpLog> queryForListDate(IpLogQueryObject qo);
}
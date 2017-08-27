package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.VisitInfo;
import com.feiran.zg.core.base.query.VisitInfoQueryObject;

import java.util.List;

public interface VisitInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(VisitInfo record);

    VisitInfo selectByPrimaryKey(String id);

    List<VisitInfo> selectAll();

    int updateByPrimaryKey(VisitInfo record);

    List<VisitInfo> getDoctorVisitInfosByDoctorId(Long publisher_id);

    // 高级查询相关
    // 查询符合条件的数据的条数
    int quetryForCount(VisitInfoQueryObject qo);
    // 查询符合条件的当前页的数据
    List<VisitInfo> queryForListDate(VisitInfoQueryObject qo);
}
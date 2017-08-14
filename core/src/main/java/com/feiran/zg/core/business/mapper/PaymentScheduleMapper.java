package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.query.PaymentScheduleQueryObject;
import com.feiran.zg.core.business.domain.PaymentSchedule;

import java.util.List;

public interface PaymentScheduleMapper {

    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentSchedule record);

    // 高级查询
    int queryForCount(PaymentScheduleQueryObject qo);
    List<PaymentSchedule> queryForListData(PaymentScheduleQueryObject qo);
}
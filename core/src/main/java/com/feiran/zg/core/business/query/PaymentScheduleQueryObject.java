package com.feiran.zg.core.business.query;

import com.feiran.zg.core.base.query.AuditQueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 还款计划查询对象
 * Created by zhangguangfei on 2017/2/13.
 */
@Setter@Getter
public class PaymentScheduleQueryObject extends AuditQueryObject{
    private Long loginInfoId;// 当前用户只能查看自己的还款对象
    private Long bidRequestId;// 查询属于哪一个还款标的还款对象
}

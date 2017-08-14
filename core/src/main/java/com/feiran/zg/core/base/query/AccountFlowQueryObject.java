package com.feiran.zg.core.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 流水查询对象
 * Created by zhangguangfei on 2017/2/18.
 */
@Getter@Setter
public class AccountFlowQueryObject extends AuditQueryObject {
    private Long loginInfoId;// 用户只能查询自己的流水信息
}

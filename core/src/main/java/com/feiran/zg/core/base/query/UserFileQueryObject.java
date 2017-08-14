package com.feiran.zg.core.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户风控材料查询对象
 * Created by zhangguangfei on 2017/2/8.
 */
@Setter@Getter
public class UserFileQueryObject extends AuditQueryObject {
    private Long applierId;
}

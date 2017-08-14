package com.feiran.zg.core.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基本的审核队形
 * Created by zhangguangfei on 2017/2/5.
 */
@Setter
@Getter
abstract public class BaseAuditDomain extends BaseDomain {
    public static final int STATE_NORMAL = 0;// 正常状态,即待审核状态
    public static final int STATE_AUDIT = 1;// 审核通过
    public static final int STATE_REJECT = 2;// 审核失败

    protected LoginInfo auditor;// 审核人
    protected LoginInfo applier;// 申请人

    protected Date applyTime;// 申请时间
    protected Date auditTime;// 审核时间

    protected String remark;// 审核备注

    protected int state;// 记录审核状态(正常状态,即待审核状态、审核通过、审核失败)

    public String getStateDisplay(){
        switch (state){
            case STATE_NORMAL:
                return "待审核";
            case STATE_AUDIT:
                return "审核通过";
            case STATE_REJECT:
                return "审核拒绝";
            default:
                return "";
        }
    }
}

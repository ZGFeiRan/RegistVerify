package com.feiran.zg.core.business.domain;

import com.feiran.zg.core.base.domain.BaseAuditDomain;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 借款标的审核记录历史
 * Created by zhangguangfei on 2017/2/8.
 */
@Setter@Getter
@Alias("bidRequestAuditHistory")
public class BidRequestAuditHistory extends BaseAuditDomain {
    public static final int PUBLISH_AUDIT = 0;// 发标前审核
    public static final int FULL_AUDIT_1 = 1;// 满标一审
    public static final int FULL_AUDIT_2 = 2;// 满标二审

    private Long bidRequestId;// 审核历史对应的申请的Id
    private int auditType;// 审核类型(发标前审核、满标一审、满标二审)

    public String getAuditTypeDisplay() {
        switch (this.auditType) {
            case PUBLISH_AUDIT:
                return "发标前审核";
            case FULL_AUDIT_1:
                return "满标一审";
            case FULL_AUDIT_2:
                return "满标二审";
            default:
                return "";
        }
    }
}

package com.feiran.zg.core.business.query;

import com.feiran.zg.core.base.query.AuditQueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 线下支付查询对象
 * Created by zhangguangfei on 2017/2/10.
 */
@Setter@Getter
public class RechargeOffLineQueryObject extends AuditQueryObject {
    private Long applierId;// 当前登录的用户只能只能查询自己提交的线下充值单,所以把充值单的申请者的id注入,作为sql语句的筛选条件
    private Long bankInfoid;
    private String tranceCode;

    public String getTranceCode(){
        return StringUtils.hasLength(this.tranceCode)?this.tranceCode:null;
    }
}

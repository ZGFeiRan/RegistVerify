package com.feiran.zg.core.base.query;

import com.feiran.zg.core.base.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 认证查询的基类
 * Created by zhangguangfei on 2017/2/5.
 */
@Getter@Setter
public class AuditQueryObject extends QueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private int state = -1;

    public Date getBeginDate(){
        return beginDate!=null? DateUtil.getBeginDate(beginDate):null;
    }

    public Date getEndDate(){
        return endDate!=null?DateUtil.getEndDate(endDate):null;
    }

}

package com.feiran.zg.core.base.query;

import com.feiran.zg.core.base.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;


import java.util.Date;

/**
 * 登录日志查询对象
 * Created by zhangguangfei on 2017/1/14.
 */
@Getter@Setter
public class IpLogQueryObject extends QueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String userName;
    private int state = -1;
    private int userType = -1;


    public Date getBeginDate(){
        return beginDate!=null? DateUtil.getBeginDate(beginDate):null;
    }

    public Date getEndDate(){
        return endDate!=null?DateUtil.getEndDate(endDate):null;
    }

    // 过滤掉空字符串
    public String  getUserName(){
        return StringUtils.hasLength(userName)?userName:null;
    }
}

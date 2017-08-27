package com.feiran.zg.core.base.query;
import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.Position;
import com.feiran.zg.core.base.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 登录日志查询对象
 * Created by FeiRan_ZG on 2017/8/27.
 */
@Getter
@Setter
public class VisitInfoQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitBeginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitEndDate;

    private DoctorInfo doctorInfo;

    private Long publisherId;

    private String town;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginPublishDate;// 发布日期

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endPublishDate;// 发布日期

    private Integer isOverdue;// 是否过期

//    public Date getVisitBeginDate(){
//        return visitBeginDate!=null? DateUtil.getBeginDate(visitBeginDate):null;
//    }
//
//    public Date getVisitEndDate(){
//        return visitEndDate!=null?DateUtil.getEndDate(visitEndDate):null;
//    }
//
    public Date getBeginPublishDate(){
        return beginPublishDate!=null?DateUtil.getBeginDate(beginPublishDate):null;
    }

    public Date getEndPublishDate(){
        return endPublishDate!=null?DateUtil.getEndDate(endPublishDate):null;
    }





}

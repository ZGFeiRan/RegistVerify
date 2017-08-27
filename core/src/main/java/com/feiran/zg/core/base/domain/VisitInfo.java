package com.feiran.zg.core.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 医生发布坐诊信息的对象
 * Created by zhangguangfei on 2017/8/26.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("visitInfo")
public class VisitInfo {
    private String id;

    private Long publisherId;// 外键，关联发布者，即医生

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String town;

    private Integer toplimit;

    private Integer version;

    private Position position;// 坐诊的场所

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

    private Integer isOverdue;// 坐诊信息是否已经过期，0代表已过期，1代表未过期

    public String getPosition(){
        String province = position.getProvince();
        if (position.getProvince() !=null && (position.getProvince().contains("北京") ||
                position.getProvince().contains("天津") || position.getProvince().contains("上海")
                || position.getProvince().contains("重庆"))){
            province = position.getProvince()+"(直辖市)";
        }

        return province + "  "+(position.getCity()!=null?position.getCity()+" ":"")+position.getCounty()+"  "+position.getCountryside();
    }

    public String getIsOverdue(){
        return this.isOverdue==1?"未过期":"已过期";
    }
}
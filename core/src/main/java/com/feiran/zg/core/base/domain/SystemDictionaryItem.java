package com.feiran.zg.core.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhangguangfei on 2017/1/13.
 */
@Setter@Getter
@Alias("systemDictionaryItem")
public class SystemDictionaryItem extends BaseDomain{
    private Long parentId;
    private String title;// 数据字典明细显示的名称
    private Integer sequence;// 数据字典明细在该分类中的排序

    public String getJsonString(){
        Map<String ,Object > json = new HashMap<String ,Object >();
        json.put("id",id);
        json.put("parentId",this.parentId);
        json.put("title",this.title);
        json.put("sequence",this.sequence);
        String jsonString = JSONObject.toJSONString(json);
        return jsonString;
    }
}

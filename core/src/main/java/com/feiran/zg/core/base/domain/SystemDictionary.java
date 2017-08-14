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
@Alias("systemDictionary")
public class SystemDictionary extends BaseDomain{
    private String title;// 数据字典分类名称
    private String sn;// 数据字典分类编码

    public String getJsonString(){
        Map<String ,Object > json = new HashMap<String ,Object >();
        json.put("id",id);
        json.put("title",this.title);
        json.put("sn",this.sn);
        String jsonString = JSONObject.toJSONString(json);
        return jsonString;
    }
}

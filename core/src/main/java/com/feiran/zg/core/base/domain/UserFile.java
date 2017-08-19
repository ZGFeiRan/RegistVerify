package com.feiran.zg.core.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;

/**
 * 风控材料对象
 * Created by zhangguangfei on 2017/2/4.
 */

@Setter@Getter
@Alias("userFile")
public class UserFile extends BaseAuditDomain {
    private String image;
    private SystemDictionaryItem fileType;// 风控材料的类型

    public String getJsonString(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",id);
        hashMap.put("applier",this.applier.getUserName());
        hashMap.put("fileType",this.fileType.getTitle());
        hashMap.put("image",image);
        String jsonString = JSONObject.toJSONString(hashMap);
        return jsonString;
    }
}

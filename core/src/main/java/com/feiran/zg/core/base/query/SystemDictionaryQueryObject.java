package com.feiran.zg.core.base.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Created by zhangguangfei on 2017/1/18.
 */
@Setter@Getter
public class SystemDictionaryQueryObject extends QueryObject {

     private String keyWord;
    private Long parentId;
    public String getKeyWord(){
        return StringUtils.hasLength(keyWord)?keyWord:null;
    }
}

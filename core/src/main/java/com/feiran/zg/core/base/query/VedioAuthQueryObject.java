package com.feiran.zg.core.base.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 视频认证查询对象
 * Created by zhangguangfei on 2017/2/5.
 */
@Setter@Getter
public class VedioAuthQueryObject extends AuditQueryObject {

    private String keyWord;

    public String getKyeWord(){
        return StringUtils.hasLength(this.keyWord)?this.keyWord:null;
    }
}

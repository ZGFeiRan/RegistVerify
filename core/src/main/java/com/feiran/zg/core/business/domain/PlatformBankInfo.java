package com.feiran.zg.core.business.domain;

import com.alibaba.fastjson.JSON;
import com.feiran.zg.core.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;

/**
 * 平台账户信息
 * Created by zhangguangfei on 2017/2/10.
 */
@Setter@Getter
@Alias("platformBankInfo")
public class PlatformBankInfo extends BaseDomain {
    private String bankName;// 银行名称
    private String accountName;// 开户人姓名
    private String accountNumber;// 银行账号
    private String bankForkName;// 开户行支行名称
    private String iconCls;// 开户行的图标

    public String getJsonString(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("bankName",bankName);
        map.put("accountName",accountName);
        map.put("accountNumber",accountNumber);
        map.put("bankForkName",bankForkName);
        return JSON.toJSONString(map);
    }
}

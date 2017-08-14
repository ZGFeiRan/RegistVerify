package com.feiran.zg.core.base.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证对象
 * Created by zhangguangfei on 2017/2/3.
 */
@Setter@Getter
@Alias("realAuth")
public class RealAuth extends BaseAuditDomain {
    public static final int SEX_MALE = 0;// 男
    public static final int SEX_FEMALE = 1 ;// 女

    private int sex;// 性别
    private LoginInfo applier;// 申请人
    private String realName;// 真实姓名
    private String idNumber;// 身份证号码

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;// 出生日期

    private String address;// 家庭地址
    private String image1;// 验证图片1,正面
    private String image2;// 验证图片2,反面

    public String getSexDisplay(){
        return sex==SEX_FEMALE?"女":"男";
    }

    public String getJsonString(){
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("applier", this.applier.getUserName());
        json.put("realName", realName);
        json.put("idNumber", idNumber);
        json.put("sex", this.getSexDisplay());
        json.put("bornDate", DateFormat.getDateInstance(DateFormat.FULL).format(this.bornDate));
        json.put("address", address);
        json.put("image1", image1);
        json.put("image2", image2);
        String jsonString = JSON.toJSONString(json);
        return jsonString;
    }

    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     *
     * @return
     */
    public String getAnonymousRealName() {

        if (StringUtils.hasLength(this.realName)){
            String replace = "";
            replace += realName.charAt(0);
            int length = realName.length();
            for (int i = 1;i<length;i++){
                replace += "*";
            }
            return replace;
        }

        return realName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     *
     * @return
     */
    public String getAnonymousIdNumber() {
        if (StringUtils.hasLength(idNumber)) {
            int len = idNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += idNumber.charAt(i);
                }
            }
            return replace;
        }
        return idNumber;
    }

    /**
     * 获取用户住址的隐藏字符串
     * @return
     */
    public String getAnonymousAddress() {
        if (StringUtils.hasLength(address) && address.length() > 4) {
            String last = address.substring(address.length() - 4,
                    address.length());
            String stars = "";
            for (int i = 0; i < address.length() - 4; i++) {
                stars += "*";
            }
            return stars + last;
        }
        return address;
    }
}

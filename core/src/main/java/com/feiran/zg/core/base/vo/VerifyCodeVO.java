package com.feiran.zg.core.base.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 用来包装验证码、时间、手机号
 * Created by zhangguangfei on 2017/1/16.
 */
@Setter@Getter
@AllArgsConstructor@NoArgsConstructor
public class VerifyCodeVO {
    private String verifyCode;// 验证码
    private String phoneNumber;// 手机号
    private Date sendTime;// 发送的时间
}

package com.feiran.zg.core.base.service;

/**
 * 验证码相关服务
 * Created by zhangguangfei on 2017/1/16.
 */
public interface IVerifyCodeService {
    // 发送验证码
    void sendVerifyCode(String phoneNumber);

    /**
     * 校验验证码
     * @param phoneNumber
     * @param code
     * @return
     */
    boolean validate(String phoneNumber, String code);
}

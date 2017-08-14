package com.feiran.zg.core.base.service;

/**
 * 邮件绑定相关业务
 * Created by zhangguangfei on 2017/1/17.
 */
public interface IEmailService {
    void sendVerifyEmail(String email);
}

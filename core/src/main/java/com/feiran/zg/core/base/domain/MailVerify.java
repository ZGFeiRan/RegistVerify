package com.feiran.zg.core.base.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 邮箱验证对象
 */
@Setter@Getter
@AllArgsConstructor@NoArgsConstructor
public class MailVerify extends BaseDomain{
    private String uuid;
    private Long loginInfoId;
    private Date sendTime;
    private String email;
}
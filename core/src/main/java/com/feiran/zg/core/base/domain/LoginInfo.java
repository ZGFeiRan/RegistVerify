package com.feiran.zg.core.base.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Setter
@Getter
@Alias("loginInfo")
public class LoginInfo extends BaseDomain{
    public static final int USER_CLIENT = 0;// 前台用户
    public static final int USER_MANAGER = 1;// 后台管理员


    public static final int STATE_NORMAL = 1;// 用户的正常状态
    public static final int STATE_LOCK = 0;// 用户的锁定状态

    private String userName;

    private String password;

    private Integer state;// 用户的状态(正常状态,锁定状态)

    private int userType = USER_CLIENT;

    private Date lastLoginDateTime;// 上一次登录时间，集最近一次登录时间
}
package com.feiran.zg.core.base.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by zhangguangfei on 2017/1/14.
 * 登陆日志
 */
@Setter@Getter
@Alias("ipLog")
public class IpLog extends BaseDomain{
    public static final int STATE_SUCCESS = 1;// 登录成功
    public static final int STATE_FAILED=0;// 登录失败

    private String ip;// 登录的ip地址

    private Date loginTime;// 登录的时间

    private int state = STATE_SUCCESS;// 登录状态,本次登录是否成功

    private String userName;// 本次登录用户的用户名

    private int userType;

    public String getStateDisplay(){
        return this.state==STATE_SUCCESS?"登录成功":"登录失败";
    }

    public String getUserTypeDisplay() {
        return this.userType==LoginInfo.USER_CLIENT?"前端用户":"后台管理员";
    }
}

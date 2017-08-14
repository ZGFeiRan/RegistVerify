package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.LoginInfo;

import java.util.List;
import java.util.Map;

/**
 * 登录相关服务
 * Created by zhangguangfei on 2017/1/11.
 */
public interface ILoginInfoService {

     // 注册
    void register(String userName,String password);

    // 检查用户名是否已经存在,如果用户名已经存在,就标示该用户名已经被注册过,则返回true,否则返回false。
    boolean checkUserNameExist(String userName);

    // 用户登录
    LoginInfo login(String userName,String password,String ip,int userType);

    // 系统启动的时候,还没有管理员,所以要在系统启动的时候,创建第一个管理员
    void initAdmin();

    /**
     * 自动补全
     */
    List<Map<String ,Object>> autoComplate(String keyWord);
}

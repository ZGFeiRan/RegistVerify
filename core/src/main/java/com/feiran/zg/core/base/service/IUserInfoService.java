package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.UserInfo;

/**
 * Created by zhangguangfei on 2017/1/13.
 * 用户信息服务相关信息
 */
public interface IUserInfoService {
    /**
     * 更新操作,注意:在这里要处理乐观锁
     * @param userInfo
     */
    void update(UserInfo userInfo);

    /**
     * 添加用户信息
     * @param userInfo
     */
    void add(UserInfo userInfo);

    /**
     * 得到当前登录用户的用户信息
     * @return
     */
    UserInfo getCurrent();

    // 根据手机号和验证码校验是否正确,校验成功后绑定手机号
    void bindPhone(String phoneNumber,String code);
    // 根据uuid验证邮件,验证通过后绑定邮箱
    void bindEmail(String uuid);

    /**
     * 保存或修改用户的信息
     * @param userInfo
     * @return
     */
    void saveOrUpdateUserInfo(UserInfo userInfo);

    /**
     * 根据id值查询出相应的UserInfo对象
     * @param id
     * @return
     */
    UserInfo getById(Long id);
}

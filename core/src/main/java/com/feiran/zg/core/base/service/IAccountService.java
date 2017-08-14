package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.Account;

/**
 * Created by zhangguangfei on 2017/1/13.
 * 账户相关服务
 */
public interface IAccountService {
    /**
     * 更新操作,注意:在这里要处理乐观锁
     * @param account
     */
    void update(Account account);

    /**
     * 添加账户
     * @param account
     */
    void add(Account account);

    /**
     * 得到当前登录用户的账户
     * @return
     */
    Account getCurrent();

    /**
     * 根据id查询
     */
    Account getById(Long id);

    /**
     * 定时检查系统中Account数据库表中的数据是否被恶意篡改过
     */
    void chechAccountVerify();
}

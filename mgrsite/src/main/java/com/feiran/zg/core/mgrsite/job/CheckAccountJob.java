package com.feiran.zg.core.mgrsite.job;

import com.feiran.zg.core.base.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 检查数据库中的Account表中的数据是否被恶意篡改过的作业类
 * Created by zhangguangfei on 2017/2/16.
 */
@Component("checkAccountJobClass")
public class CheckAccountJob {

    @Autowired
    private IAccountService accountService;

    public void checkAccounts(){
        this.accountService.chechAccountVerify();
    }

}

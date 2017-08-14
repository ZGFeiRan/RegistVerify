package com.feiran.zg.core.mgrsite.listener;

import com.feiran.zg.core.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 系统启动的时候,创建平台账户
 * Created by zhangguangfei on 2017/2/11.
 */
@Component
public class InitSystemAccountListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ISystemAccountService systemAccountService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.systemAccountService.initSystemAccount();
    }
}

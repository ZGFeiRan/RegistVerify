package com.feiran.zg.core.mgrsite.listener;

import com.feiran.zg.core.base.service.ILoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 创建第一个管理员的监听器
 *
 * 只需要spring容器来管理这个监听器,就完成了监听器的注册
 *
 * Created by zhangguangfei on 2017/1/15.
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent>{
    @Autowired
    private ILoginInfoService loginInfoService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // spring容器启动完毕之后,要紧接着执行的内容
        this.loginInfoService.initAdmin();
    }
}

package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.business.event.RealAuthSuccessEvetn;
import com.feiran.zg.core.business.event.VedioAuthSuccessEvetn;
import com.feiran.zg.core.base.domain.VedioAuth;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 发送有点的Service
 * Created by zhangguangfei on 2017/2/14.
 */
@Service
public class SmsServiceImpl implements ApplicationListener<ApplicationEvent> {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof RealAuthSuccessEvetn){
            RealAuthSuccessEvetn realAuthSuccessEvetn = (RealAuthSuccessEvetn) event;
            this.sendSms(realAuthSuccessEvetn.getRealAuth());
        }else if (event instanceof VedioAuthSuccessEvetn){
            VedioAuthSuccessEvetn vedioAuthSuccessEvetn = (VedioAuthSuccessEvetn) event;
            this.sendSms(vedioAuthSuccessEvetn.getVedioAuth());
        }
    }

    public void sendSms(RealAuth realAuth) {
        System.out.println(realAuth.getRealName()+"实名认证成功的站内信息");
    }

    public void sendSms(VedioAuth vedioAuth) {
        System.out.println(vedioAuth.getRemark()+"视频认证成功的站内信息");
    }
}

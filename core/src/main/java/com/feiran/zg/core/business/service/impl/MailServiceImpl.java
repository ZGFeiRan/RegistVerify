package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.business.event.RealAuthSuccessEvetn;
import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.domain.VedioAuth;
import com.feiran.zg.core.business.event.VedioAuthSuccessEvetn;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Created by zhangguangfei on 2017/2/14.
 */
@Service
public class MailServiceImpl implements ApplicationListener<ApplicationEvent> {


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
        System.out.println(realAuth.getRealName()+"实名认证成功的邮件");
    }

    public void sendSms(VedioAuth vedioAuth) {
        System.out.println(vedioAuth.getRemark()+"视频认证成功的邮件");
    }
}

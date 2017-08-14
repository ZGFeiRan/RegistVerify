package com.feiran.zg.core.business.event;

import com.feiran.zg.core.base.domain.VedioAuth;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 实名认证成功的事件
 * Created by zhangguangfei on 2017/2/14.
 */
@Getter
public class VedioAuthSuccessEvetn extends ApplicationEvent {
    private VedioAuth vedioAuth;
    public VedioAuthSuccessEvetn(Object source,VedioAuth vedioAuth) {
        super(source);
        this.vedioAuth = vedioAuth;
    }
}

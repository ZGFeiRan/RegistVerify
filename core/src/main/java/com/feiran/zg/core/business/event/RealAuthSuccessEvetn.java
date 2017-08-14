package com.feiran.zg.core.business.event;

import com.feiran.zg.core.base.domain.RealAuth;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 实名认证成功的事件
 * Created by zhangguangfei on 2017/2/14.
 */
@Getter
public class RealAuthSuccessEvetn extends ApplicationEvent {
    private RealAuth realAuth;
    public RealAuthSuccessEvetn(Object source,RealAuth realAuth) {
        super(source);
        this.realAuth = realAuth;
    }
}

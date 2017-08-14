package com.feiran.zg.core.mgrsite.web.inteceptor;

import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.base.domain.LoginInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台登录检查过滤器
 * Created by zhangguangfei on 2017/1/15.
 */
public class LoginCheckInteceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserContext.setQuest(request);
        LoginInfo loginInfo = UserContext.getCurrent();
        if (loginInfo==null){
            response.sendRedirect("/login.html");
            return false;
        }

        return true;
    }

}

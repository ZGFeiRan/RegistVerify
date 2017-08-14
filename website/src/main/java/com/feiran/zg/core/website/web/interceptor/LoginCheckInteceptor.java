package com.feiran.zg.core.website.web.interceptor;

import com.feiran.zg.core.base.anno.RequireLogin;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台登录检查过滤器
 * Created by zhangguangfei on 2017/1/15.
 */
public class LoginCheckInteceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
// 对于前端的拦截控制而言,有些页面是要求用户登录后才能访问,而有些页面即使用户不登录也能访问。如淘宝、京东的网站,即使用户不登录,有些页面也是可以访问得到的。
        UserContext.setQuest(request);

        // 对应静态资源handler对应的静态类为DefaultServletHttpRequestHandler,此时不需要做URL控制
        // 当handler是HandlerMethod数据类型的对象时,此时才表示需要访问控制器的方法,此时才需要URL控制权
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            if (handlerMethod.getMethodAnnotation(RequireLogin.class)!=null && UserContext.getCurrent()==null){
                response.sendRedirect("/login.html");
                return false;
            }
        }

//        return true;
        return super.preHandle(request,response,handler);
    }

}

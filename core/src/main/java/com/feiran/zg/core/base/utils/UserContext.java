package com.feiran.zg.core.base.utils;

import com.feiran.zg.core.base.vo.VerifyCodeVO;
import com.feiran.zg.core.base.domain.LoginInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by zhangguangfei on 2017/1/3.
 *
 * 用户上下文
 */
public class UserContext {

    public static final String LOININFO_IN_SESSION = "loginInfo";
    public static final String VERIFYCODE_IN_SESSION = "verifycode_in_session";

    private static HttpSession getSession() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        return session;
    }

    /***
     * 把当前登录成功的用户放到会话session中
     * @param loginInfo
     */
    public static void putCurrent(LoginInfo loginInfo){
        getSession().setAttribute(LOININFO_IN_SESSION,loginInfo);
    }

    /***
     * 从当前会话session中获取当前登录的用户
     * @return
     */
    public static LoginInfo getCurrent(){
        return (LoginInfo) getSession().getAttribute(LOININFO_IN_SESSION);
    }


    private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<HttpServletRequest>();

    public static HttpServletRequest getQuest(){
        return local.get();
    }

    public static void setQuest(HttpServletRequest request){
        local.set(request);
    }

    // 存放VerifyCodeVO
    public static void putVerifyCodeVO(VerifyCodeVO verifyCodeVO) {
        getSession().setAttribute(VERIFYCODE_IN_SESSION,verifyCodeVO);
    }

    public static VerifyCodeVO getVerifyCodeVO(){
        return (VerifyCodeVO) getSession().getAttribute(VERIFYCODE_IN_SESSION);
    }
}

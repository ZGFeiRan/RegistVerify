package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.service.ILoginInfoService;
import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.domain.IpLog;
import com.feiran.zg.core.base.domain.LoginInfo;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.mapper.IpLogMapper;
import com.feiran.zg.core.base.mapper.LoginInfoMapper;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.base.utils.MD5;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguangfei on 2017/1/11.
 */
@Service
public class LoginInfoServiceImpl implements ILoginInfoService {
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IpLogMapper ipLogMapper;

    @Override
    public void register(String userName, String password) {
        // 判断用户名是否存在
        int count = loginInfoMapper.countUserByUserName(userName);
        // 如果用户名存在,则抛出异常
        if (count>0){
            throw new RuntimeException("该用户名已存在");
        }else {
            // 如果用户名不存在,则注册
            LoginInfo info = new LoginInfo();
            info.setUserName(userName);
            info.setPassword(MD5.encode(password));
            info.setState(LoginInfo.STATE_NORMAL);// 设置用户的状态为正常状态,如果账户有异常就设置为锁定状态
            info.setUserType(LoginInfo.USER_CLIENT);// 设置用户为前台客户,而不是后台管理人员
            loginInfoMapper.insert(info);

            // 创建属于该用户的账户信息
            Account account = new Account();
            account.setId(info.getId());
            this.accountService.add(account);

            // 创建属于该用户的用户信息
            UserInfo userInfo = new UserInfo();
            userInfo.setId(info.getId());
            this.userInfoService.add(userInfo);
        }
    }

    @Override
    public boolean checkUserNameExist(String userName) {
        return loginInfoMapper.countUserByUserName(userName) > 0;
    }

    @Override
    public LoginInfo login(String userName, String password,String ip,int userType) {
        // 每当有用户登录的时候,就创建一个登录日志对象
        IpLog ipLog = new IpLog();
        ipLog.setUserName(userName);
        ipLog.setLoginTime(new Date());
        ipLog.setUserType(userType);
        ipLog.setIp(ip);

        // 根据用户名和密码查询LoginInfo
        LoginInfo loginInfo = this.loginInfoMapper.login(userName,MD5.encode(password),userType);
        if (loginInfo!=null){
            // 如果查询到,则登录成功,登录成功后把LoginInfo放到Session中
            UserContext.putCurrent(loginInfo);
            // 登录成功的话,设置IpLog对象的状态为success
            ipLog.setState(IpLog.STATE_SUCCESS);
        }else {
            // 登录失败的话,设置IpLog对象的状态为failed
            ipLog.setState(IpLog.STATE_FAILED);
        }


        // 往数据库中添加一条数据
        ipLogMapper.insert(ipLog);

        // 登录成功则返回当前登录对象。登录失败,则返回一个null。
        return loginInfo;
    }

    @Override
    public void initAdmin() {
        // 查询数据库中是否已经有管理员
       int count = this.loginInfoMapper.queryCountByUserType(LoginInfo.USER_MANAGER);
        // 如果没有,就创建一个
        if(count<=0){
            LoginInfo admin = new LoginInfo();
            admin.setUserType(LoginInfo.USER_MANAGER);
            admin.setUserName(BidConst.DEFUALT_ADMIN_NAME);
            admin.setPassword(MD5.encode(BidConst.DEFUALT_ADMIN_PASSWROD));
            admin.setState(LoginInfo.STATE_NORMAL);
            this.loginInfoMapper.insert(admin);
        }
    }

    @Override
    public List<Map<String, Object>> autoComplate(String keyWord) {
        return this.loginInfoMapper.autoComplate(keyWord);
    }
}

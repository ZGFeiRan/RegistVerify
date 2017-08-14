package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.mapper.MailVerifyMapper;
import com.feiran.zg.core.base.mapper.UserInfoMapper;
import com.feiran.zg.core.base.service.IEmailService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.service.IVerifyCodeService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.base.utils.BitStatesUtils;
import com.feiran.zg.core.base.utils.DateUtil;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.base.domain.MailVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhangguangfei on 2017/1/13.
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private IVerifyCodeService verifyCodeService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private MailVerifyMapper mailVerifyMapper;


    /**
     * 带有处理乐观锁的更新操作
     * @param userInfo
     */
    @Override
    public void update(UserInfo userInfo) {
        int count = this.userInfoMapper.updateByPrimaryKey(userInfo);
        if (count<=0){
            // 一旦抛出异常,SpringMVC就会回滚事物,更新操作就会被回滚。
            throw new RuntimeException("乐观锁失败,UserInfo: "+userInfo.getId());
        }
    }

    @Override
    public void add(UserInfo userInfo) {
        this.userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo getCurrent() {
         return this.userInfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
    }

    @Override
    public void bindPhone(String phoneNumber, String code) {
         // 1、校验 验证码,判断之前是否已经绑定过手机
        boolean result = this.verifyCodeService.validate(phoneNumber,code);
        if (result){
            // 2、如果校验成功,说明之前没有绑定过手机号,就绑定手机号并设置状态码
            UserInfo current = this.getCurrent();
            if (!current.getIsBindPhone()){// 当前用户没有绑定过手机号时,才允许该用户绑定,如果绑定过了,就不能再次绑定,但可以通过点击"修改绑定手机号"按钮去修改绑定的手机号
                current.setPhoneNumber(phoneNumber);// 绑定手机号
                current.setBitState(BitStatesUtils.OP_BIND_PHONE);// 设置验证码
                this.update(current);
            }else {
                throw new RuntimeException("您已经绑定过手机号了,一个账号只能绑定一个手机号");
            }
        }else{
            throw new RuntimeException("验证码校验失败");
        }
    }

    @Override
    public void bindEmail(String uuid) {
        // 根据uuid查询MailVerify对象
        MailVerify mv = mailVerifyMapper.selectByUUID(uuid);

        if (mv!=null && (DateUtil.getSecondInterval(mv.getSendTime(),new Date()) <= BidConst.VERIFYEMAIL_VALID_DAY*24*3600)){// 验证邮件在有效期内,则继续一下操作
            // 根据查询到的mv中的loginInfoId获取申请绑定邮箱的用户
            UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(mv.getLoginInfoId());

            if (!userInfo.getIsBindEmail()){// 如果用户还没有绑定邮箱,则继续下一步操作
                userInfo.setBitState(BitStatesUtils.OP_BIND_EMAIL);// 设置状态码
                userInfo.setEmail(mv.getEmail());// 设置邮箱
                this.update(userInfo);// 更新userInfo对象
                return;
            }else {
                throw new RuntimeException("您已经为该账号绑定过邮箱,一个账号只能绑定一个邮箱!!!");
            }
        }else {
            throw new RuntimeException("验证链接已失效,请重新申请邮箱绑定");
        }
    }
    @Override
    public void saveOrUpdateUserInfo(UserInfo userInfo) {
        // 获取当前登录的用户,
        UserInfo current = this.getCurrent();
        // 给当前登录的用户设置前台传递过来的参数
        current.setEducationBackground(userInfo.getEducationBackground());
        current.setKidCount(userInfo.getKidCount());
        current.setHouseCondition(userInfo.getHouseCondition());
        current.setIncomeGrade(userInfo.getIncomeGrade());
        userInfo.setMarriage(userInfo.getMarriage());
        // 如果当前用户没有填写基本资料,则绑定一个
        if (!current.getIsBasicInfo()){
            current.setState(BitStatesUtils.OP_BASIC_INFO);
        }
        // 使用带有处理乐观锁的更新操作的方法更新数据
        this.update(current);
    }

    @Override
    public UserInfo getById(Long id) {
        return this.userInfoMapper.selectByPrimaryKey(id);
    }

}

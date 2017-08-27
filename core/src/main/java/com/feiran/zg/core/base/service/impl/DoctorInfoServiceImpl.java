package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.*;
import com.feiran.zg.core.base.mapper.DoctorDegreeMapper;
import com.feiran.zg.core.base.mapper.DoctorInfoMapper;
import com.feiran.zg.core.base.mapper.DoctorTitleMapper;
import com.feiran.zg.core.base.mapper.MailVerifyMapper;
import com.feiran.zg.core.base.service.IDoctorInfoService;
import com.feiran.zg.core.base.service.IEmailService;
import com.feiran.zg.core.base.service.IVerifyCodeService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.base.utils.BitStatesUtils;
import com.feiran.zg.core.base.utils.DateUtil;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/17.
 */
@Service
public class DoctorInfoServiceImpl implements IDoctorInfoService{

    @Autowired
    private DoctorInfoMapper doctorInfoMapper;
    @Autowired
    private IVerifyCodeService verifyCodeService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private MailVerifyMapper mailVerifyMapper;
    @Autowired
    private DoctorTitleMapper doctorTitleMapper;
    @Autowired
    private DoctorDegreeMapper doctorDegreeMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return doctorInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DoctorInfo record) {
        return doctorInfoMapper.insert(record);
    }

    @Override
    public DoctorInfo selectByPrimaryKey(Long id) {
        return doctorInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DoctorInfo> selectAll() {
        return doctorInfoMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(DoctorInfo record) {
        int count = doctorInfoMapper.updateByPrimaryKey(record);
        if (count<=0){
            // 一旦抛出异常,SpringMVC就会回滚事物,更新操作就会被回滚。
            throw new RuntimeException("乐观锁失败,UserInfo: "+record.getId());
        }
        return count;
    }

    @Override
    public DoctorInfo getCurrent() {
        LoginInfo current = UserContext.getCurrent();
        Long id = UserContext.getCurrent().getId();
        return doctorInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void bindPhone(String phoneNumber, String code) {
        // 1、校验 验证码,判断之前是否已经绑定过手机
        boolean result = this.verifyCodeService.validate(phoneNumber,code);
        if (result){
            // 2、如果校验成功,说明之前没有绑定过手机号,就绑定手机号并设置状态码
            DoctorInfo current = this.getCurrent();
            if (!current.getIsBindPhone()){// 当前用户没有绑定过手机号时,才允许该用户绑定,如果绑定过了,就不能再次绑定,但可以通过点击"修改绑定手机号"按钮去修改绑定的手机号
                current.setPhoneNumber(phoneNumber);// 绑定手机号
                current.setBitState(BitStatesUtils.OP_BIND_PHONE);// 设置验证码
                this.updateByPrimaryKey(current);
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
            DoctorInfo userInfo = this.doctorInfoMapper.selectByPrimaryKey(mv.getLoginInfoId());

            if (!userInfo.getIsBindEmail()){// 如果用户还没有绑定邮箱,则继续下一步操作
                userInfo.setBitState(BitStatesUtils.OP_BIND_EMAIL);// 设置状态码
                userInfo.setEmail(mv.getEmail());// 设置邮箱
                this.updateByPrimaryKey(userInfo);// 更新userInfo对象
                return;
            }else {
                throw new RuntimeException("您已经为该账号绑定过邮箱,一个账号只能绑定一个邮箱!!!");
            }
        }else {
            throw new RuntimeException("验证链接已失效,请重新申请邮箱绑定");
        }
    }

    @Override
    public List<DoctorTitle> getDoctorTitle() {
        return this.doctorTitleMapper.selectAll();
    }

    @Override
    public List<DoctorDegree> getDoctorDegree() {
        return this.doctorDegreeMapper.selectAll();
    }

    @Override
    public void update(DoctorInfo doctorInfo) {
        DoctorInfo current = this.getCurrent();
        current.setPhoneNumber(doctorInfo.getPhoneNumber());
        current.setDoctorSex(doctorInfo.getDoctorSex());
        current.setHospitalName(doctorInfo.getHospitalName());
        current.setOfficesName(doctorInfo.getOfficesName());
        current.setDoctorAbout(doctorInfo.getDoctorAbout());
        current.setDoctorDegree(doctorInfo.getDoctorDegree());
        current.setDoctorForte(doctorInfo.getDoctorForte());
        current.setDoctorTitle(doctorInfo.getDoctorTitle());
        current.setDoctorImg(doctorInfo.getDoctorImg());
        // 如果当前用户没有填写基本资料,则绑定一个
        if (!doctorInfo.getIsBasicInfo()){
            doctorInfo.setState(BitStatesUtils.OP_BASIC_INFO);
        }
        // 使用带有处理乐观锁的更新操作的方法更新数据
        this.updateByPrimaryKey(current);
    }


}

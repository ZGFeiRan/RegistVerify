package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.DoctorDegree;
import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.DoctorTitle;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/17.
 *
 * 医生信息的Service
 */
public interface IDoctorInfoService {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorInfo record);

    DoctorInfo selectByPrimaryKey(Long id);

    List<DoctorInfo> selectAll();

    int updateByPrimaryKey(DoctorInfo record);

    /**
     * 得到当前登录用户的用户信息
     * @return
     */
    DoctorInfo getCurrent();

    // 根据手机号和验证码校验是否正确,校验成功后绑定手机号
    void bindPhone(String phoneNumber,String code);
    // 根据uuid验证邮件,验证通过后绑定邮箱
    void bindEmail(String uuid);

    /**
     * 获取所有的医生职称
     * @return
     */
    List<DoctorTitle> getDoctorTitle();
    /**
     * 获取所有的医生学位
     * @return
     */
    List<DoctorDegree> getDoctorDegree();


    void update(DoctorInfo doctorInfo);
}

package com.feiran.zg.core.base.domain;

import com.feiran.zg.core.base.utils.BitStatesUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by FeiRan_ZG on 2017/8/12.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInfo {
    // id
    private long id;
    // 用户状态码,判断用户是否已经绑定了手机、是否实名认证等等
    private long bitState = 0;
    // 医生姓名（真实姓名）
    private String doctorName;
    // 医生性别
    private String doctorSex;
    // 医院名称
    private String hospitalName;
    // 科室名称
    private String officesName;
    // 医生照片
    private String doctorImg;
    // 医生职称
    private String doctorTitle;
    // 教学支职称
    private String teachTitle;
    // 行政职位
    private String doctorAdministrative;
    // 学位
    private String doctorDegree;
    // 医生特长
    private String doctorForte;
    // 医生关于
    private String doctorAbout;
    // 用户手机号
    private String phoneNumber;
    // 用户邮箱
    private String email;
    // 用户对应的实名认证对象的id
    private Long realAuthId;
    // 逻辑删除: 0代表没有删除，1代表已删除
    private int isDelete = 0;

    @Override
    public String toString() {
        return "DoctorInfo [id=" + id + ", doctorName=" + doctorName + ", doctorSex=" + doctorSex + ", hospitalName="
                + hospitalName + ", officesName=" + officesName + ", doctorImg=" + doctorImg + ", doctorTitle="
                + doctorTitle + ", teachTitle=" + teachTitle + ", doctorAdministrative=" + doctorAdministrative
                + ", doctorDegree=" + doctorDegree + ", doctorForte=" + doctorForte + ", doctorAbout=" + doctorAbout
                + ", phoneNumber=" + phoneNumber + ", email=" + email+ ", isDelete=" + isDelete + "]";
    }


    // 判断用户是否已经实名认真
    public boolean getIsRealAuth(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_REAL_AUTH);
    }

    // 判断用户是否填写基本资料
    public boolean getIsBasicInfo(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BASIC_INFO);
    }

    // 设置用户的状态码
    public void setState(Long state){
        this.bitState = BitStatesUtils.addState(this.bitState, state);
    }

    // 移除用户的状态码
    public void removeState(Long state){
        this.bitState = BitStatesUtils.removeState(this.bitState,state);
    }

    // 判断用户是否绑定手机
    public boolean getIsBindPhone(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BIND_PHONE);
    }

    // 判断用户是否绑定邮箱
    public boolean getIsBindEmail(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BIND_EMAIL);
    }


}


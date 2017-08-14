package com.feiran.zg.core.base.domain;

import com.feiran.zg.core.base.utils.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

/**
 * 用户对象
 * Created by zhangguangfei on 2017/1/13.
 */
@Setter@Getter
@Alias("userInfo")
public class UserInfo extends BaseDomain{
    private int version;// 版本号,用来处理第一类和第二类数据丢失
    private long bitState = 0;// 用户状态码,判断用户是否已经绑定了手机、是否实名认证等等
    private String realName;// 用户的真实姓名
    private String idNumber;// 用户的手机号
    private String phoneNumber;// 用户手机号
    private String email;// 用户邮箱
    private int authScore;// 用户的风控总分数

    private Long realAuthId;// 用户对应的实名认证对象的id

    private SystemDictionaryItem incomeGrade;// 收入
    private SystemDictionaryItem marriage;// 婚姻情况
    private SystemDictionaryItem kidCount;// 子女情况
    private SystemDictionaryItem educationBackground;// 学历
    private SystemDictionaryItem houseCondition;// 住房条件

    // 判断用户是否已经视频认证
    public boolean getIsVedioAuth(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_VEDIO_AUTH);
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

    // 判断用户是否有其他的借款标申请尚在审核流程中
    public boolean getIsBidRequestInProcess(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
    }

    // 判断用户是否已经绑定过银行卡
    public boolean getIsBindBank(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BIND_BANKINFO);
    }

    // 判断用户是否已经绑定过银行卡
    public boolean getHasMoneyWithdrawInProcess(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS);
    }


    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     *
     * @return
     */
    public String getAnonymousRealName() {

        if (StringUtils.hasLength(this.realName)){
            String replace = "";
            replace += realName.charAt(0);
            int length = realName.length();
            for (int i = 1;i<length;i++){
                replace += "*";
            }
            return replace;
        }

        return realName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     *
     * @return
     */
    public String getAnonymousIdNumber() {
        if (StringUtils.hasLength(idNumber)) {
            int len = idNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += idNumber.charAt(i);
                }
            }
            return replace;
        }
        return idNumber;
    }


}

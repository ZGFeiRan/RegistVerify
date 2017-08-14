package com.feiran.zg.core.business.domain;

import com.feiran.zg.core.base.domain.BaseDomain;
import com.feiran.zg.core.base.domain.LoginInfo;
import org.springframework.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户银行卡对象
 * Created by zhangguangfei on 2017/2/12.
 */
@Setter@Getter
public class UserBankInfo extends BaseDomain{
    private String bankName;// 银行名称
    private String accountName;// 银行卡的开户人姓名
    private String accountNumber;// 银行卡的账号
    private String bankForkName;// 开户支行

    private LoginInfo loginInfo;

    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     */
    public String getAnonymousRealName () {
        if (StringUtils.hasLength(this.accountName)) {
            int len = accountName.length();
            String replace = "";
            replace += accountName.charAt(0);
            for (int i = 1; i < len; i++) {
                replace += "*";
            }
            return replace;
        }
        return accountName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     */
    public String getAnonymousAccountNumber() {
        if (StringUtils.hasLength(this.accountNumber)) {
            int len = this.accountNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += this.accountNumber.charAt(i);
                }
            }
            return replace;
        }
        return this.accountNumber;
    }
}

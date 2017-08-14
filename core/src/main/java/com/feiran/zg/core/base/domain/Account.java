package com.feiran.zg.core.base.domain;

import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.base.utils.MD5;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * Created by zhangguangfei on 2017/1/13.
 */
@Setter@Getter
@Alias("account")
public class Account extends BaseDomain{
    private int version;// 版本号
    private String tradePassword;// 交易密码
    private BigDecimal usableAmount = BidConst.ZERO;// 账户可用余额
    private BigDecimal freezedAmount = BidConst.ZERO;// 账户冻结余额
    private BigDecimal unReceivePrincipal = BidConst.ZERO;// 账户待收本金
    private BigDecimal unReceiveInterest = BidConst.ZERO;// 账户待收利息
    private BigDecimal unReturnAmount = BidConst.ZERO;// 账户待还本息金额
    private BigDecimal remainBorrowLimit = BidConst.INIT_BORROW_LIMIT;// 账户剩余授信额度
    private BigDecimal borrowLimit = BidConst.INIT_BORROW_LIMIT;// 账户授信额度

    private String verity;// 用于校验数据库中的资金有没有被人恶意篡改过

    /**
     * 获取用来判断数据库中的资金是否被恶意篡改过的校验码
     * @return
     */
    public String  getVerity(){
        return MD5.encode(this.id+" "+this.usableAmount+" "+this.freezedAmount);
    }

    public boolean isValidate(){
        return MD5.encode(this.id+" "+this.usableAmount+" "+this.freezedAmount).equals(this.verity);
    }

    // 计算账户总金额
    public BigDecimal getTotalAmount(){
        return this.usableAmount.add(freezedAmount).add(this.unReceivePrincipal);
    }
}

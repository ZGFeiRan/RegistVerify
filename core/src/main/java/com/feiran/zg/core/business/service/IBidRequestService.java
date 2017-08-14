package com.feiran.zg.core.business.service;

import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.business.domain.BidRequest;
import com.feiran.zg.core.business.domain.BidRequestAuditHistory;
import com.feiran.zg.core.business.query.BidRequestQueryObject;
import com.feiran.zg.core.business.query.PaymentScheduleQueryObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借款相关服务
 * Created by zhangguangfei on 2017/2/8.
 */
public interface IBidRequestService {
    /**
     * 乐观锁控制
     */
    void update(BidRequest bidRequest);

    /**
     * 判断用户是否可以申请借款
     * @param userInfo
     * @return
     */
    boolean canApply(UserInfo userInfo);

    /**
     * 申请借款
     * @param bidRequest
     */
    void apply(BidRequest bidRequest);

    // 高级查询
    PageResult queryForPageResult(BidRequestQueryObject qo);

    /**
     * 查询结果集列表
     */
    List<BidRequest> queryForListData(BidRequestQueryObject qo);

    /**
     * 发标前的审核操作
     */
    void publishAudit(Long id,String remark,int state);

    /**
     * 通过id值获取对象的BidRequest对象
     * @param id
     * @return
     */
    BidRequest getById(Long id);

    /**
     * 获取当前借款标的所有审核历史对象
     * @param id
     * @return
     */
    List<BidRequestAuditHistory> listAuditHistoryByBidRequestId(Long id);

    /**
     * 前台客户投标
     */
    void bid(Long bidRequestId, BigDecimal amount);

    /**
     * 满标一审
     */
    void fullAudit1(Long id,String remark,int state);

    /**
     * 满标二审
     */
    void fullAudit2(Long id,String remark,int state);

    /**
     * 还款列表
     */
    PageResult queryForPaymentSchedulePageResult(PaymentScheduleQueryObject qo);

    /**
     * 执行还款操作
     */
    void returnMoney(Long id);
}

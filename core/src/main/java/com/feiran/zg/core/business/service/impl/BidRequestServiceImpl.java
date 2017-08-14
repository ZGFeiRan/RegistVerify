package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.business.domain.*;
import com.feiran.zg.core.business.mapper.*;
import com.feiran.zg.core.business.query.BidRequestQueryObject;
import com.feiran.zg.core.business.query.PaymentScheduleQueryObject;
import com.feiran.zg.core.business.service.IBidRequestService;
import com.feiran.zg.core.business.util.CalculatetUtil;
import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.utils.BitStatesUtils;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.service.IAccountFlowService;
import com.feiran.zg.core.business.service.ISystemAccountService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by zhangguangfei on 2017/2/8.
 */
@Service
public class BidRequestServiceImpl implements IBidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

    @Override
    public void update(BidRequest bidRequest) {
        int count = this.bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (count <= 0) {
            throw new RuntimeException("乐观锁失败,BidRequest: " + bidRequest.getId());
        }
    }

    @Override
    public boolean canApply(UserInfo userInfo) {
        // 判断当前用户是否已经实名认证、视频认证、填写了基本资料、风控材料分数
        return userInfo.getIsRealAuth() && userInfo.getIsVedioAuth() && userInfo.getIsBasicInfo() && userInfo.getAuthScore() >= BidConst.CREDIT_BORROW_SCORE;
        //&& userInfo.getAuthScore()>=BidConst.CREDIT_BORROW_SCORE
    }

    @Override
    public void apply(BidRequest bidRequest) {
        // 得到当前用户
        UserInfo userInfo = this.userInfoService.getCurrent();
        Account account = accountService.getCurrent();
        // 判断:
        if (this.canApply(userInfo)// 1、用户必须满足借款要求
                && !userInfo.getIsBidRequestInProcess()// 2、用户没有其他借款申请尚在申请流程中
                && bidRequest.getBidRequestAmount().compareTo(BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0
                && bidRequest.getBidRequestAmount().compareTo(account.getRemainBorrowLimit()) <= 0// 3、判断用户此次的:系统最小借款金额<=借款金额<=剩余信用额度,
                && bidRequest.getCurrentRate().compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0
                && bidRequest.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0// 4、系统最小年化利率<=年化利率<=系统最大年化利率
                && bidRequest.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0 // 5、系统最小投标额度<=投标额度
                ) {
            // 以上判断都成功之后,进入借款申请流程:
            // 1、创建一个BidRequest对象,设置属性,并保存
            BidRequest br = new BidRequest();
            br.setBidRequestAmount(bidRequest.getBidRequestAmount());
            br.setCurrentRate(bidRequest.getCurrentRate());
            br.setDescription(bidRequest.getDescription());
            br.setDisableDays(bidRequest.getDisableDays());
            br.setMinBidAmount(bidRequest.getMinBidAmount());
            br.setMonthes2Return(bidRequest.getMonthes2Return());
            br.setReturnType(bidRequest.getReturnType());
            br.setTitle(bidRequest.getTitle());

            br.setBidRequestType(BidConst.BIDREQUEST_TYPE_NORMAL);// 设置借款类型为:普通信用标
            br.setApplyTime(new Date());// 设置申请时间
            br.setCreateUser(UserContext.getCurrent());
            br.setTotalRewardAmount(
                    CalculatetUtil.calTotalInterest(
                            br.getReturnType(),
                            br.getBidRequestAmount(),
                            br.getCurrentRate(),
                            br.getMonthes2Return())
            );
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);// 设置借款表的状态为"发布前审核状态"
            this.bidRequestMapper.insert(br);// 保存
            // 2、给用户添加一个状态码,指明该用户有一个处于"招标状态"的借款标
            userInfo.setState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            this.userInfoService.update(userInfo);
        }
    }

    /**
     * 借款标的高级查询
     */
    @Override
    public PageResult queryForPageResult(BidRequestQueryObject qo) {
        int count = this.bidRequestMapper.queryForCount(qo);
        if (count <= 0) {
            return PageResult.empty(qo.getPageSize());
        }

        List<BidRequest> listDate = this.bidRequestMapper.queryForListDate(qo);

        return new PageResult(listDate, count, qo.getCurrentPage(), qo.getPageSize());
    }

    /**
     * 发标前审核
     * @param id
     * @param remark
     * @param state
     */
    @Override
    public void publishAudit(Long id, String remark, int state) {
        // 根据传进来的id值获取到借款对象,
        BidRequest bidRequest = this.bidRequestMapper.selectByPrimaryKey(id);
        // 判断当前这个借款对象的状态是否处于发标前审核状态
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING) {
            // 创建一个审核历史对象,并设置相关数据
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            history.setApplier(bidRequest.getCreateUser());
            history.setApplyTime(bidRequest.getApplyTime());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setAuditType(BidRequestAuditHistory.PUBLISH_AUDIT);// 设置当前标的状态为"发标前审核"状态
            history.setState(state);
            history.setBidRequestId(bidRequest.getId());
            history.setRemark(remark);
            this.bidRequestAuditHistoryMapper.insert(history);// 出入一条新数据
            bidRequest.setNote(remark);
            // 如果审核通过
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                //  1、修改借款标状态,设置其处于招标状态
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
                //  2、修改借款标的信息
                bidRequest.setPublishTime(new Date());
                bidRequest.setDisableDate(DateUtils.addDays(bidRequest.getPublishTime(), bidRequest.getDisableDays()));
            } else {// 如果审核失败
                //  1、修改借款状态,设置其为"发标钱审核拒绝"状态
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
                //  2、把给申请这个标的用户设置的"有一个标处于审核流程"的状态码删除掉
                UserInfo userInfo = this.userInfoService.getById(bidRequest.getCreateUser().getId());
                userInfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
                this.userInfoService.update(userInfo);
            }
            this.update(bidRequest);
        }
    }

    @Override
    public BidRequest getById(Long id) {
        return this.bidRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BidRequestAuditHistory> listAuditHistoryByBidRequestId(Long id) {
        return this.bidRequestAuditHistoryMapper.listAuditHistoryByBidRequestId(id);
    }

    @Override
    public List<BidRequest> queryForListData(BidRequestQueryObject qo) {
        return this.bidRequestMapper.queryForListDate(qo);
    }

    /**
     * 用户对借款标进行投标
     */
    @Override
    public void bid(Long bidRequestId, BigDecimal amount) {
        // 得到借款标对象
        BidRequest br = this.bidRequestMapper.selectByPrimaryKey(bidRequestId);
        // 获取当前账户
        Account account = this.accountService.getCurrent();
        // 判断本次投标是否有效,有效则执行投标流程
        if (br.getBidRequestState() == BidConst.BIDREQUEST_STATE_BIDDING // 1、所投的这个借款标当前处于招标状态
                && UserContext.getCurrent().getId() != br.getCreateUser().getId() // 2、当前用户不是这个借款标的发起人
                && (amount.compareTo(br.getMinBidAmount()) >= 0 // 3、投标金额>=最小投标金额
                || br.getRemainAmount().compareTo(br.getMinBidAmount()) <= 0)// 3、or距离满标的剩余金额<=最小投标金额
                && amount.compareTo(br.getRemainAmount()) <= 0 // 4、投标金额<=距离满标的剩余金额
                && amount.compareTo(account.getUsableAmount()) <= 0 // 5、投标金额<=当前用户的剩余可用金额
                ) {
            // 执行投标流程
            // 1、创建一个投标对象,并设置相关属性
            Bid bid = new Bid();
            bid.setActualRate(br.getCurrentRate());
            bid.setAvailableAmount(amount);
            bid.setBidRequestId(bidRequestId);
            bid.setBidRequestState(br.getBidRequestState());
            bid.setBidRequestTitle(br.getTitle());
            bid.setBidTime(new Date());
            bid.setBidUser(UserContext.getCurrent());

            this.bidMapper.insert(bid);// 保存投标对象

            // 2、修改借款标对象中的一些属性
            br.setBidCount(br.getBidCount() + 1);
            br.setCurrentSum(br.getCurrentSum().add(amount));

            // 3、减少当前用户的账户上的可用金额,增加冻结金额
            account.setUsableAmount(account.getUsableAmount().subtract(amount));
            account.setFreezedAmount(account.getFreezedAmount().add(amount));

            // 4、生成一个投标流水
            this.accountFlowService.insertBidFlow(account, bid);

            // 5、本次投资后,需要判断该标是否已经被投满,即当前这个标已经处于满标状态了。如果投满,则进入满标一审状态
            if (br.getBidRequestAmount().equals(br.getCurrentSum())) {
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
                // 当借款标的状态放生改变的时候,要同时去修改属于该借款标的所有投标对象的状态
                this.bidMapper.updateBidsState(bidRequestId, BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
            }
            this.update(br);
            this.accountService.update(account);
        }
    }

    /**
     * 满标一审
     */
    @Override
    public void fullAudit1(Long id, String remark, int state) {
        // 得到借款对象,判断借款对象的状态
        BidRequest bidRequest = this.getById(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1) {
            // 创建一个历史审核对象
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            // 并设置相关属性,然后保存
            history.setApplier(bidRequest.getCreateUser());
            history.setApplyTime(new Date());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setBidRequestId(bidRequest.getId());
            history.setAuditType(BidRequestAuditHistory.FULL_AUDIT_1);
            history.setRemark(remark);
            history.setState(state);

            this.bidRequestAuditHistoryMapper.insert(history);

            // 判断审核状态,如果前台传过来的是"通过审核",则继续执行后续操作
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 审核通过
                //  1、修改标的状态,设置其为满标二审状态
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
                //  2、修改所有投了次标的投标对象的状态
                this.bidMapper.updateBidsState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
            } else {
                // 审核失败
                //  1、修改标的状态,设置状态为满标审核被拒绝
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
                //  2、修改所有投了次标的投标对象的状态
                this.bidMapper.updateBidsState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_REJECTED);
                //  3、遍历每一个投了次标的投标对象,然后找到投标对象所属的用户,减少该用户的账户上的冻结金额,增加其可用金额
                returnMoney(bidRequest);

                //  4、把分配给这个标所属用户的"有一个处于招标状态"的状态移除掉
                UserInfo userInfo = this.userInfoService.getById(bidRequest.getCreateUser().getId());
                userInfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);

                this.userInfoService.update(userInfo);
            }
            this.update(bidRequest);
        }
    }

    /**
     * 满标一审、满标二审核被拒绝、投标人车标、流标的时候,即借款标借款失败的时候,退钱给投标人
     *
     * @param bidRequest
     */
    private void returnMoney(BidRequest bidRequest) {
        HashMap<Long, Account> map = new HashMap<>();
        for (Bid bid : bidRequest.getBids()) {
            Long accountId = bid.getBidUser().getId();
            Account account = map.get(accountId);
            if (account == null) {
                account = this.accountService.getById(accountId);
                map.put(accountId, account);// 因为一个用户可能给同一个借款标投标多次,为避免在遍历投标对象的时候重复发送sql查询账户对象,可以把用户的账户保存起来。
            }
            account.setFreezedAmount(account.getFreezedAmount().subtract(bid.getAvailableAmount()));
            account.setUsableAmount(account.getUsableAmount().add(bid.getAvailableAmount()));

            // 创建一条取消投标流水
            this.accountFlowService.cancleBidFlow(account, bid);
        }
        // 统一更新map中的账户的信息
        for (Account account : map.values()) {
            this.accountService.update(account);
        }
    }

    /**
     * 满标二审
     */
    @Override
    public void fullAudit2(Long id, String remark, int state) {
        // 得到借款对象,判断借款对象的状态
        BidRequest bidRequest = this.getById(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2) {
            // 创建一个历史审核对象,
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            // 设置历史审核对象相关属性,然后保存
            history.setApplier(bidRequest.getCreateUser());
            history.setApplyTime(new Date());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setBidRequestId(bidRequest.getId());
            history.setAuditType(BidRequestAuditHistory.FULL_AUDIT_2);
            history.setRemark(remark);
            history.setState(state);

            this.bidRequestAuditHistoryMapper.insert(history);

            // 判断审核状态,如果前台传过来的是"通过审核",则继续执行后续操作
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 审核通过
                //  1、从借款对象思考
                //     **1.1 修改借款表的状态,设置其处于"还款中"的状态
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);
                //     **1.2 修改属于该借款标的所有投标对象的状态
                this.bidMapper.updateBidsState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_PAYING_BACK);

                //  2、从还款人角度思考
                //     **2.1 拿到借款人账户,借款人账户的可用余额增加;生成一条账户流水对象
                Account account = this.accountService.getById(bidRequest.getCreateUser().getId());
                account.setUsableAmount(account.getUsableAmount().add(bidRequest.getBidRequestAmount()));
                this.accountFlowService.borrowSuccessFlow(account, bidRequest);
                //     **2.2 去掉借款人的借款状态码
                // 因为无论审核通过与否都要去掉用户的借款状态码,所以把这个功能的代买快写在了这个方法的最后边

                //     **2.3 借款人的可用信用额度减少
                account.setRemainBorrowLimit(account.getRemainBorrowLimit().subtract(bidRequest.getBidRequestAmount()));
                //     **2.4 借款人的待还借款总额增加
                account.setUnReturnAmount(account.getUnReturnAmount().add(bidRequest.getBidRequestAmount()).add(bidRequest.getTotalRewardAmount()));// 代还款中金额等于借款到的本金加上相应的总利息

                //     **2.5 支付借款手续费,生成一条支付手续费流水对象
                BigDecimal borrowChargeFee = CalculatetUtil.calAccountManagementCharge(bidRequest.getBidRequestAmount());
                System.out.println("借款手续费是:"+borrowChargeFee);
                account.setUsableAmount(account.getUsableAmount().subtract(borrowChargeFee));
                this.accountFlowService.insertBorrowChargeFee(borrowChargeFee, account, bidRequest);

                //     **2.6 平台收取手续费;平台的账户余额增加,生成一条平台账户流水对象
                this.systemAccountService.chargeBorrowFee(borrowChargeFee, bidRequest);
                // 更新账户对象
                this.accountService.update(account);

                //  3、从投标人角度思考
                //     **3.1 遍历该借款标的投标对象
                HashMap<Long, Account> accountMap = new HashMap<>();
                for (Bid bid : bidRequest.getBids()) {
                    Long accountId = bid.getBidUser().getId();
                    Account bidAccount = accountMap.get(accountId);
                    if (bidAccount == null) {
                        bidAccount = this.accountService.getById(accountId);
                        accountMap.put(accountId, bidAccount);
                    }
                    // **3.2 减少账户冻结金额,增加一条投标成功的流水
                    bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
                    this.accountFlowService.insertBidSuccessFlow(bid, bidAccount);

                }

                //  4、满标二审成功后,就要进入还款流程,那么满标二审成功后还款流程有没有什么需要对交代的
                //     **4.1 生成针对这个借款标的还款信息和汇款信息
                List<PaymentSchedule> scheduleList = createPaymentSchedule(bidRequest);
                //     **4.2 增加待收利息和待收本金
                for (PaymentSchedule schedule : scheduleList) {
                    for (PaymentScheduleDetail detail : schedule.getPaymentScheduleDetails()) {
                        Account ac = accountMap.get(detail.getToLoginInfoId());
                        // 增加待收利息
                        ac.setUnReceiveInterest(ac.getUnReceiveInterest().add(detail.getInterest()));
                        // 增加待收本金
                        ac.setUnReceivePrincipal(ac.getUnReceivePrincipal().add(detail.getPrincipal()));
                    }

                }

                // 对accountMap中保存的account进行统一更新
                for (Account account1 : accountMap.values()) {
                    this.accountService.update(account1);
                }

            } else {
                // 审核失败
                //  1、修改标的状态,设置状态为满标审核被拒绝
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
                //  2、修改所有投了次标的投标对象的状态
                this.bidMapper.updateBidsState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
                //  3、遍历每一个投了次标的投标对象,然后找到投标对象所属的用户,减少该用户的账户上的冻结金额,增加其可用金额
                returnMoney(bidRequest);

            }
            //  把分配给这个标所属用户的"有一个处于招标状态"的状态移除掉
            UserInfo userInfo = this.userInfoService.getById(bidRequest.getCreateUser().getId());
            userInfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            this.userInfoService.update(userInfo);
        }
        this.update(bidRequest);
    }

    /**
     * 创建针对某个借款标的还款信息和汇款明细
     */
    private List<PaymentSchedule> createPaymentSchedule(BidRequest bidRequest) {
        List<PaymentSchedule> schedules = new ArrayList<>();

        // 累加利息
        BigDecimal totalInterest = BidConst.ZERO;
        // 累加本金
        BigDecimal totalPrincipal = BidConst.ZERO;

        int monthes2Return = bidRequest.getMonthes2Return();
        for (int i = 0; i < monthes2Return; i++) {
            // 循环遍历,为每一期还款创建一个还款对象(每一次都是一期的还款对象)
            PaymentSchedule ps = new PaymentSchedule();
            ps.setBidRequestId(bidRequest.getId());
            ps.setBidRequestTitle(bidRequest.getTitle());
            ps.setBidRequestType(bidRequest.getBidRequestType());
            ps.setBorrowUser(bidRequest.getCreateUser());
            ps.setDeadLine(DateUtils.addMonths(bidRequest.getPublishTime(), i + 1));
            ps.setMonthIndex(i + 1);
            ps.setPayDate(new Date());
            ps.setState(BidConst.PAYMENT_STATE_NORMAL);
            ps.setReturnType(bidRequest.getReturnType());
            if (i < monthes2Return - 1) {
                ps.setInterest(CalculatetUtil.calMonthlyInterest(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), i + 1, bidRequest.getMonthes2Return()));
                ps.setTotalAmount(CalculatetUtil.calMonthToReturnMoney(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), i + 1, bidRequest.getMonthes2Return()));
                ps.setPrincipal(ps.getTotalAmount().subtract(ps.getInterest()));

                // 累加
                totalInterest = totalInterest.add(ps.getInterest());
                totalPrincipal = totalPrincipal.add(ps.getPrincipal());
            } else {
                ps.setInterest(bidRequest.getTotalRewardAmount().subtract(totalInterest));
                ps.setPrincipal(bidRequest.getBidRequestAmount().subtract(totalPrincipal));
                ps.setTotalAmount(ps.getInterest().add(ps.getPrincipal()));
            }
            this.paymentScheduleMapper.insert(ps);

            createPaymentScheduleDetail(ps, bidRequest);
            schedules.add(ps);
        }
        return schedules;
    }

    /**
     * 创建针对每一期还款对象的回款对象
     */
    private void createPaymentScheduleDetail(PaymentSchedule ps, BidRequest bidRequest) {
        // 累加利息
        BigDecimal totalInterest = BidConst.ZERO;
        // 累加总金额
        BigDecimal totalAmount = BidConst.ZERO;

        for (int i = 0; i < bidRequest.getBids().size(); i++) {
            Bid bid = bidRequest.getBids().get(i);
            // 循环遍历,针对该借款标的每一个投标对象创建一个回款对象
            PaymentScheduleDetail detail = new PaymentScheduleDetail();
            detail.setReturnType(ps.getReturnType());
            detail.setBidAmount(ps.getPrincipal().multiply(new BigDecimal(bidRequest.getMonthes2Return())));
            detail.setBidId(bid.getId());

            detail.setBidRequestId(bidRequest.getId());
            detail.setDeadline(ps.getDeadLine());
            detail.setFromLoginInfo(bidRequest.getCreateUser());
            detail.setPayDate(new Date());
            detail.setMonthIndex(ps.getMonthIndex());
            detail.setPaymentScheduleId(ps.getId());
            detail.setToLoginInfoId(bid.getBidUser().getId());
            detail.setBidRequestTitle(bid.getBidRequestTitle());
            if (i < bidRequest.getBids().size() - 1) {
                //回款利息=投标资金/借款标总金额*本期还款利息
                detail.setInterest(bid.getAvailableAmount().divide(bidRequest.getBidRequestAmount(), BidConst.CAL_SCALE, RoundingMode.HALF_UP).multiply(ps.getInterest()).setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP));
                //回款利息=投标资金/借款标总金额*本期还款本金
                detail.setPrincipal(bid.getAvailableAmount().divide(bidRequest.getBidRequestAmount(), BidConst.CAL_SCALE, RoundingMode.HALF_UP).multiply(ps.getPrincipal()).setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP));
                detail.setTotalAmount(detail.getInterest().add(detail.getPrincipal()));

                // 累加
                totalInterest = totalInterest.add(detail.getInterest());
                totalAmount = totalAmount.add(detail.getTotalAmount());

            } else {
                detail.setInterest(ps.getInterest().subtract(totalInterest));
                detail.setTotalAmount(ps.getTotalAmount().subtract(detail.getInterest()));
                detail.setPrincipal(detail.getTotalAmount().subtract(detail.getInterest()));
            }

            this.paymentScheduleDetailMapper.insert(detail);

            ps.getPaymentScheduleDetails().add(detail);
        }
    }

    /**
     * 还款对象的高级查询
     */
    @Override
    public PageResult queryForPaymentSchedulePageResult(PaymentScheduleQueryObject qo) {
        int count = this.paymentScheduleMapper.queryForCount(qo);
        if (count <= 0) {
            return PageResult.empty(qo.getPageSize());
        }

        List<PaymentSchedule> listData = this.paymentScheduleMapper.queryForListData(qo);

        return new PageResult(listData, count, qo.getCurrentPage(), qo.getPageSize());
    }

    /**
     * 还款操作
     */
    @Override
    public void returnMoney(Long id) {
        // 得到这一期的还款对象
        PaymentSchedule schedule = this.paymentScheduleMapper.selectByPrimaryKey(id);
        // 获取借款人(即借款人)在本平台的账户
        Account returnAccount = this.accountService.getById(schedule.getBorrowUser().getId());
        // 判断还款对象的状态
        if (schedule != null && schedule.getState() == BidConst.PAYMENT_STATE_NORMAL // 1、还款对象处于还款状态
                && returnAccount.getUsableAmount().compareTo(schedule.getTotalAmount()) >= 0 // 2、账户余额>=还款所需金额
                && schedule.getBorrowUser().getId() == UserContext.getCurrent().getId() // 3、当前登录的用户就是还款人。防止A用户用B用户的账户还A用户借的款,因为每一期还款的id是可以在页面上查看到的
                ) {
            // 对于这一期的还款对象而言,要修改其状态为已还,并设置还款日期
            schedule.setState(BidConst.PAYMENT_STATE_DONE);
            schedule.setPayDate(new Date());

            // 执行还款:
            // 对于借款人来说(即对于还款人来说)
            // 1、可用余额减少,生成还款流水对象
            returnAccount.setUsableAmount(returnAccount.getUsableAmount().subtract(schedule.getTotalAmount()));
            this.accountFlowService.insertReturnMoneyFlow(returnAccount, schedule);
            // 2、待还金额减少
            returnAccount.setUnReturnAmount(returnAccount.getUnReturnAmount().subtract(schedule.getTotalAmount()));
            // 3、剩余信用额度增加
            returnAccount.setRemainBorrowLimit(returnAccount.getRemainBorrowLimit().add(schedule.getPrincipal()));

            // 对于投资人来说(即对于收款人来说)
            Map<Long, Account> accountMap = new HashMap<>();
            // 1、遍历属于该还款对象的还款明细对象
            for (PaymentScheduleDetail detail : schedule.getPaymentScheduleDetails()) {
                // 2、得到投资人账户,增加投资人账户的可用余额,生成一条成功收款的流水
                Long toLoginInfoId = detail.getToLoginInfoId();
                Account account = accountMap.get(toLoginInfoId);
                if (account == null) {
                    account = this.accountService.getById(toLoginInfoId);
                    accountMap.put(toLoginInfoId, account);
                }
                // 增加投资人账户的可用余额
                account.setUsableAmount(account.getUsableAmount().add(detail.getPrincipal()));
                // 生成一条成功收款的流水
                this.accountFlowService.insertReceiveMoneyFlow(account,detail);
                // 3、减少该投资人的待收本金和待收利息
                account.setUnReceivePrincipal(account.getUnReceivePrincipal().subtract(detail.getPrincipal()));
                account.setUnReceiveInterest(account.getUnReceiveInterest().subtract(detail.getInterest()));
                // 4、支付利息管理费,生成支付利息管理费的流水
                BigDecimal interestManagerChargeFee = CalculatetUtil.calInterestManagerCharge(detail.getInterest());
                account.setUsableAmount(account.getUsableAmount().subtract(interestManagerChargeFee));
                this.accountFlowService.insertInterestManagerChargeFee(account,detail,interestManagerChargeFee);
                // 5、系统账户收取利息管理费
                this.systemAccountService.chargeInterestFee(interestManagerChargeFee,detail);

                // 设置每一期回款对象的相关属性
                detail.setPayDate(new Date());
                this.paymentScheduleDetailMapper.updateByPrimaryKey(detail);
            }
            // 对accounMap中的account进行统一更新操作
            for (Account account : accountMap.values()) {
                this.accountService.update(account);
            }


            // 对于借款标对象而言,如果当前还的是最后一期,则借款标对象变成"完成状态",修改该借款标的所有投标对象的状态
            PaymentScheduleQueryObject qo = new PaymentScheduleQueryObject();
            qo.setPageSize(-1);// 不分页查询
            qo.setLoginInfoId(UserContext.getCurrent().getId());
            qo.setBidRequestId(schedule.getBidRequestId());
            List<PaymentSchedule> listData = this.paymentScheduleMapper.queryForListData(qo);
            // 遍历借款标的还款计划,看看是否还有尚未回款的还款计划
            boolean flage = false;// false表示全部还清;true表示还有尚未还的
            for (PaymentSchedule ps : listData) {
                if (ps.getState()!=BidConst.PAYMENT_STATE_DONE){
                    flage = true;
                    break;
                }
            }
            if (!flage){// 全部还清,说明这一期是这个借款标的最后一期还款
                System.out.println("借款标已经全部还清了吗??"+flage);
                BidRequest bidRequest = this.getById(schedule.getBidRequestId());
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
                this.update(bidRequest);
                this.bidMapper.updateBidsState(schedule.getBidRequestId(),BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
            }
        }
        this.paymentScheduleMapper.updateByPrimaryKey(schedule);
        this.accountService.update(returnAccount);
    }
}










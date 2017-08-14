package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.business.event.RealAuthSuccessEvetn;
import com.feiran.zg.core.base.domain.RealAuth;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.mapper.RealAuthMapper;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.RealAuthQueryObject;
import com.feiran.zg.core.base.service.IRealAuthService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.BitStatesUtils;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/3.
 */
@Service
public class RealAuthServiceImpl implements IRealAuthService {
    @Autowired
    private RealAuthMapper realAuthMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ApplicationContext publisher;

    @Override
    public RealAuth getById(Long id) {
        return this.realAuthMapper.selectByPrimaryKey(id);
    }

    @Override
    public void apply(RealAuth r) {
        // 获取当前用户
        UserInfo current = this.userInfoService.getCurrent();
        // 判断当前用户是否能够申请实名认证
         if (!current.getIsRealAuth() && current.getRealAuthId()==null){
             // 创建一个realAuth,对其设置之后将其保存起来
             RealAuth realAuth = new RealAuth();
             realAuth.setRealName(r.getRealName());
             realAuth.setSex(r.getSex());
             realAuth.setIdNumber(r.getIdNumber());
             realAuth.setBornDate(r.getBornDate());
             realAuth.setAddress(r.getAddress());
             realAuth.setImage1(r.getImage1());
             realAuth.setImage2(r.getImage2());

             realAuth.setApplier(UserContext.getCurrent());
             realAuth.setApplyTime(new Date());
             realAuth.setState(RealAuth.STATE_NORMAL);// 设置新创建的realAuth对象的状态为正常状态,即待审核状态
             this.realAuthMapper.insert(realAuth);

             // 设置userInfo中realAuthId属性的值
             current.setRealAuthId(realAuth.getId());
             this.userInfoService.update(current);
         }
    }

    @Override
    public PageResult queryForPageResult(RealAuthQueryObject qo) {
        int count = this.realAuthMapper.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<RealAuth> listData = this.realAuthMapper.queryForListData(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        // 查询realAuth
        RealAuth realAuth = this.realAuthMapper.selectByPrimaryKey(id);
        if (realAuth!=null && realAuth.getState()==RealAuth.STATE_NORMAL){
            // 查询出申请人,如果该申请人还没有实名认证,并且realAuth处于待审核状态,就执行下面的相关审核
            UserInfo applier = this.userInfoService.getById(realAuth.getApplier().getId());
            // 设置相关属性
            realAuth.setAuditor(UserContext.getCurrent());
            realAuth.setRemark(remark);
            realAuth.setAuditTime(new Date());
            realAuth.setState(state);
            if (!applier.getIsRealAuth()){
                if (state==RealAuth.STATE_REJECT){
                    // 如果审核失败
                    //   1、把申请人的realAuthId设置为空
                    applier.setRealAuthId(null);
                }else {
                    // 如果审核成功
                    //   1、把申请人的状态码添加
                    applier.setState(BitStatesUtils.OP_REAL_AUTH);
                    //   2、设置实名认证的相关信息
                    applier.setRealName(realAuth.getRealName());
                    applier.setIdNumber(realAuth.getIdNumber());

                    // 创建实名认证成功对象,并发布实名认证成功这个事件
                    RealAuthSuccessEvetn evetn = new RealAuthSuccessEvetn(this, realAuth);
                    publisher.publishEvent(evetn);
                }
                this.userInfoService.update(applier);
            }
            this.realAuthMapper.updateByPrimaryKey(realAuth);
        }
    }
}

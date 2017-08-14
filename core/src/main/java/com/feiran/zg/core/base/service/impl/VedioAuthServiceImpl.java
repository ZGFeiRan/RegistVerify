package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.LoginInfo;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.domain.VedioAuth;
import com.feiran.zg.core.base.mapper.VedioAuthMapper;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.VedioAuthQueryObject;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.service.IVedioAuthService;
import com.feiran.zg.core.base.utils.BitStatesUtils;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.business.event.VedioAuthSuccessEvetn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/7.
 */
@Service
public class VedioAuthServiceImpl implements IVedioAuthService {
    @Autowired
    private VedioAuthMapper vedioAuthMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ApplicationContext publisher;

    @Override
    public PageResult queryForPageResult(VedioAuthQueryObject qo) {
        int count = vedioAuthMapper.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<VedioAuth> listData = vedioAuthMapper.queryForListData(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void audit(Long loginInfoValue, String remark, int state) {
        // 得到当前提交视频审核的用户
        UserInfo applier = this.userInfoService.getById(loginInfoValue);
        // 判断当前提交视频审核的的用户之前是否已经通过了视频审核。
        if (applier!=null && !applier.getIsVedioAuth()){
            // 设置好对象属性
            VedioAuth va = new VedioAuth();
            // 因为保存关联的对象时,仅仅是保存对象的id值,所以既然已经知道关联对象的id值了,就没有必要再发送一条语句去查询。直接创建出相应的对象,并把已知的id值设置进去即可。
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setId(loginInfoValue);
            va.setApplier(loginInfo);// 设置申请人
            va.setApplyTime(new Date());// 设置申请时间
            va.setAuditor(UserContext.getCurrent());// 设置审核人
            va.setAuditTime(new Date());// 设置审核时间
            va.setRemark(remark);// 设置审核备注
            va.setState(state);// 设置审核状态
            // 如果视频审核通过,添加相应的状态码
            if (state==VedioAuth.STATE_AUDIT){
                applier.setState(BitStatesUtils.OP_VEDIO_AUTH);
                this.userInfoService.update(applier);

                // 创建视频认证成功对象,并发布实名认证成功这个事件
                VedioAuthSuccessEvetn vedioAuthSuccessEvetn = new VedioAuthSuccessEvetn(this, va);
                publisher.publishEvent(vedioAuthSuccessEvetn);
            }

            this.vedioAuthMapper.insert(va);
        }
    }
}

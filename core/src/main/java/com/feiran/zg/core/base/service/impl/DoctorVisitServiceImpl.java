package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.VisitInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.VisitInfoQueryObject;
import com.feiran.zg.core.base.service.IDoctorVisitService;
import com.feiran.zg.core.base.service.IVisitInfoService;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by FeiRan_ZG on 2017/8/19.
 */
@Service
public class DoctorVisitServiceImpl implements IDoctorVisitService {
    @Autowired
    private IVisitInfoService visitInfoService;
    @Override
    public boolean canApply(DoctorInfo doctorInfo) {
        // 判断当前用户是否已经实名认证 并 完善了个人信息
        return doctorInfo.getIsRealAuth() && doctorInfo.getIsBasicInfo();
    }

    @Override
    public int insertVisitInfo(VisitInfo visitInfo){
        VisitInfo insertVisitInfo = new VisitInfo();
        insertVisitInfo.setId(UUID.randomUUID().toString());// 设置id
        insertVisitInfo.setBeginDate(visitInfo.getBeginDate());// 设置开始日期
        insertVisitInfo.setEndDate(visitInfo.getEndDate());// 设置结束日期
        insertVisitInfo.setPublisherId(UserContext.getCurrent().getId());// 设置发布者主键
        insertVisitInfo.setToplimit(visitInfo.getToplimit());// 设置上限接诊者人数
        insertVisitInfo.setTown(visitInfo.getTown());// 设置坐诊场地
        insertVisitInfo.setPublishDate(new Date());// 设置发布时间
        insertVisitInfo.setIsOverdue(1);
        insertVisitInfo.setVersion(1);
        int insert = visitInfoService.insert(insertVisitInfo);
        return insert;
    }

    @Override
    public List<VisitInfo> getDoctorVisitInfosByDoctorId(Long publisher_id) {
        List<VisitInfo> visitInfoList = this.visitInfoService.getDoctorVisitInfosByDoctorId(publisher_id);
        return visitInfoList;
    }

    @Override
    public PageResult queryForPageResult(VisitInfoQueryObject qo) {
        PageResult pageResult = this.visitInfoService.queryForPageResult(qo);
        return pageResult;
    }
}

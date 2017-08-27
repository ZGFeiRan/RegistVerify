package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.VisitInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.VisitInfoQueryObject;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/16.
 *
 * 医生发布坐诊信息的Service
 */
public interface IDoctorVisitService {
    boolean canApply(DoctorInfo doctorInfo);

    int insertVisitInfo(VisitInfo visitInfo);

    List<VisitInfo> getDoctorVisitInfosByDoctorId(Long id);

    PageResult queryForPageResult(VisitInfoQueryObject qo);
}

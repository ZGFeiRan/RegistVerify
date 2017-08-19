package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.DoctorInfo;

/**
 * Created by FeiRan_ZG on 2017/8/16.
 *
 * 医生发布坐诊信息的Service
 */
public interface IDoctorVisitService {
    boolean canApply(DoctorInfo doctorInfo);
}

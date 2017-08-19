package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.service.IDoctorVisitService;
import org.springframework.stereotype.Service;

/**
 * Created by FeiRan_ZG on 2017/8/19.
 */
@Service
public class DoctorVisitServiceImpl implements IDoctorVisitService {
    @Override
    public boolean canApply(DoctorInfo doctorInfo) {
        // 判断当前用户是否已经实名认证
        return doctorInfo.getIsRealAuth();
    }
}

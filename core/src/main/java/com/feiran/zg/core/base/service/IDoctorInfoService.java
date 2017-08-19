package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.DoctorInfo;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/17.
 *
 * 医生信息的Service
 */
public interface IDoctorInfoService {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorInfo record);

    DoctorInfo selectByPrimaryKey(Long id);

    List<DoctorInfo> selectAll();

    int updateByPrimaryKey(DoctorInfo record);

    DoctorInfo getCurrent();
}

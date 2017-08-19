package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.DoctorInfo;
import java.util.List;

public interface DoctorInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorInfo record);

    DoctorInfo selectByPrimaryKey(Long id);

    List<DoctorInfo> selectAll();

    int updateByPrimaryKey(DoctorInfo record);
}
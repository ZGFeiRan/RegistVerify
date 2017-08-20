package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.DoctorDegree;
import java.util.List;

public interface DoctorDegreeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorDegree record);

    DoctorDegree selectByPrimaryKey(Long id);

    List<DoctorDegree> selectAll();

    int updateByPrimaryKey(DoctorDegree record);
}
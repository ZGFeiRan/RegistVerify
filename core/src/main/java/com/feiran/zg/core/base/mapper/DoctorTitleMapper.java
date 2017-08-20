package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.DoctorTitle;
import java.util.List;

public interface DoctorTitleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorTitle record);

    DoctorTitle selectByPrimaryKey(Long id);

    List<DoctorTitle> selectAll();

    int updateByPrimaryKey(DoctorTitle record);
}
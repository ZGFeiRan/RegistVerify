package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.Area;
import java.util.List;

public interface AreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Area record);

    Area selectByPrimaryKey(Long id);

    List<Area> selectByParentKey(Long parentId);

    List<Area> selectAll();

    int updateByPrimaryKey(Area record);
}
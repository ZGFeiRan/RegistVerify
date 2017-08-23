package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.Area;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/22.
 */
public interface IAreaService {
    int deleteByPrimaryKey(Long id);

    int insert(Area record);

    Area selectByPrimaryKey(Long id);

    List<Area> selectByParentKey(Long parentId);

    List<Area> selectAll();

    int updateByPrimaryKey(Area record);
}

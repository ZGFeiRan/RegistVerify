package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.Area;
import com.feiran.zg.core.base.mapper.AreaMapper;
import com.feiran.zg.core.base.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/22.
 */
@Service
public class AreaServiceImpl implements IAreaService {
    @Autowired
    private AreaMapper areaMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return areaMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Area record) {
        return areaMapper.insert(record);
    }

    @Override
    public Area selectByPrimaryKey(Long id) {
        return areaMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Area> selectByParentKey(Long parentId) {
        return areaMapper.selectByParentKey(parentId);
    }

    @Override
    public List<Area> selectAll() {
        return areaMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Area record) {
        return 0;
    }
}

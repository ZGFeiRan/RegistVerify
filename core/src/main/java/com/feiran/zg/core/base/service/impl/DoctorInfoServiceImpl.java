package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.mapper.DoctorInfoMapper;
import com.feiran.zg.core.base.service.IDoctorInfoService;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/17.
 */
@Service
public class DoctorInfoServiceImpl implements IDoctorInfoService{

    @Autowired
    private DoctorInfoMapper doctorInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return doctorInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DoctorInfo record) {
        return doctorInfoMapper.insert(record);
    }

    @Override
    public DoctorInfo selectByPrimaryKey(Long id) {
        return doctorInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DoctorInfo> selectAll() {
        return doctorInfoMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(DoctorInfo record) {
        return doctorInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public DoctorInfo getCurrent() {
        return doctorInfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
    }
}

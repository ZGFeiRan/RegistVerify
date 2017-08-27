package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.VisitInfo;
import com.feiran.zg.core.base.mapper.VisitInfoMapper;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.VisitInfoQueryObject;
import com.feiran.zg.core.base.service.IVisitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/26.
 */
@Service
public class VisitInfoServiceImpl implements IVisitInfoService {
    @Autowired
    private VisitInfoMapper visitInfoMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        int i = visitInfoMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int insert(VisitInfo record){
        int insert = visitInfoMapper.insert(record);
        return insert;
    }

    @Override
    public VisitInfo selectByPrimaryKey(String id) {
        VisitInfo visitInfo = visitInfoMapper.selectByPrimaryKey(id);
        return visitInfo;
    }

    @Override
    public List<VisitInfo> selectAll() {
        List<VisitInfo> visitInfoList = visitInfoMapper.selectAll();
        return visitInfoList;
    }

    @Override
    public int updateByPrimaryKey(VisitInfo record) {
        int i = visitInfoMapper.updateByPrimaryKey(record);
        return i;
    }

    @Override
    public List<VisitInfo> getDoctorVisitInfosByDoctorId(Long publisher_id) {
        List<VisitInfo> visitInfoList = this.visitInfoMapper.getDoctorVisitInfosByDoctorId(publisher_id);
        return visitInfoList;
    }

    @Override
    public PageResult queryForPageResult(VisitInfoQueryObject qo) {
        int count = visitInfoMapper.quetryForCount(qo);
        if (count<0){
            return PageResult.empty(qo.getPageSize());
//            return new PageResult(Collections.EMPTY_LIST,count,1,query.getPageSize());
        }

        List<VisitInfo> listData = visitInfoMapper.queryForListDate(qo);

        return new PageResult(listData, count,qo.getCurrentPage(),qo.getPageSize());
    }
}

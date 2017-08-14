package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.IpLog;
import com.feiran.zg.core.base.mapper.IpLogMapper;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.IpLogQueryObject;
import com.feiran.zg.core.base.service.IIpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by zhangguangfei on 2017/1/14.
 */
@Service
public class IpLogServiceImpl implements IIpLogService{
    @Autowired
    private IpLogMapper ipLogMapper;
    @Override
    public PageResult queryForPageResult(IpLogQueryObject qo) {
        int count = ipLogMapper.quetryForCount(qo);
        if (count<0){
            return PageResult.empty(qo.getPageSize());
//            return new PageResult(Collections.EMPTY_LIST,count,1,query.getPageSize());
        }

        List<IpLog> listData = ipLogMapper.queryForListDate(qo);

        return new PageResult(listData, count,qo.getCurrentPage(),qo.getPageSize());
    }
}

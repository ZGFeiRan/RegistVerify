package com.feiran.zg.core.business.service.impl;

import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.business.domain.PlatformBankInfo;
import com.feiran.zg.core.business.mapper.PlatformBankInfoMapper;
import com.feiran.zg.core.business.query.PlatformBankInfoQueryObject;
import com.feiran.zg.core.business.service.IPlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/10.
 */
@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;

    @Override
    public PageResult queryForPageResult(PlatformBankInfoQueryObject qo) {
        int count = this.platformBankInfoMapper.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<PlatformBankInfo> listData = this.platformBankInfoMapper.queryForListData(qo);

        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(PlatformBankInfo bankInfo) {
        if (bankInfo.getId()!=null){// id值不为空,说明是更新操作
            this.platformBankInfoMapper.updateByPrimaryKey(bankInfo);
        }else {// id值为空,说明是添加操作
            this.platformBankInfoMapper.insert(bankInfo);
        }
    }

    @Override
    public List<PlatformBankInfo> queryForListData() {
        List<PlatformBankInfo> listData = this.platformBankInfoMapper.selectAll();
        return listData;
    }
}

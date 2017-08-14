package com.feiran.zg.core.business.service;

import com.feiran.zg.core.business.domain.PlatformBankInfo;
import com.feiran.zg.core.business.query.PlatformBankInfoQueryObject;
import com.feiran.zg.core.base.page.PageResult;

import java.util.List;

/**
 * 平台账户管理
 * Created by zhangguangfei on 2017/2/10.
 */
public interface IPlatformBankInfoService {

    PageResult queryForPageResult(PlatformBankInfoQueryObject qo);

    /**
     * 添加/更新操作
     */
    void saveOrUpdate(PlatformBankInfo bankInfo);

    /**
     * 查询出所有数据
     * @return
     */
    List<PlatformBankInfo> queryForListData();
}

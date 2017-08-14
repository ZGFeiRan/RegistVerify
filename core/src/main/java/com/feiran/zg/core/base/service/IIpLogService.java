package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.query.IpLogQueryObject;
import com.feiran.zg.core.base.page.PageResult;

/**
 * Created by zhangguangfei on 2017/1/14.
 */
public interface IIpLogService {
    /**
     * 高级查询加分页
     * @param qo
     * @return
     */
    PageResult queryForPageResult(IpLogQueryObject qo);
}

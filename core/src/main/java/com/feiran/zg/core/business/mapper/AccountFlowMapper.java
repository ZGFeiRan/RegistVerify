package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.base.query.AccountFlowQueryObject;
import com.feiran.zg.core.business.domain.AccountFlow;
import java.util.List;

public interface AccountFlowMapper {

    int insert(AccountFlow record);

    // 高级查询
    int queryForCount(AccountFlowQueryObject qo);
    List<AccountFlow> queryForListData(AccountFlowQueryObject qo);
}
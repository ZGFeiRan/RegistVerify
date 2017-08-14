package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.MoneyWithdraw;
import com.feiran.zg.core.business.query.MoneyWithdrawQueryObject;

import java.util.List;

public interface MoneyWithdrawMapper {

    int insert(MoneyWithdraw record);

    MoneyWithdraw selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MoneyWithdraw record);

    // 高级查询
    int queryForCount(MoneyWithdrawQueryObject qo);

    List<MoneyWithdraw> queryForListData(MoneyWithdrawQueryObject qo);


}
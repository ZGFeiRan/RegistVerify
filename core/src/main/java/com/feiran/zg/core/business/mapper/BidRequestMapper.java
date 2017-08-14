package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.BidRequest;
import com.feiran.zg.core.business.query.BidRequestQueryObject;

import java.util.List;


public interface BidRequestMapper {

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BidRequest record);

    // 高级查询
    int queryForCount(BidRequestQueryObject qo);
    List<BidRequest> queryForListDate(BidRequestQueryObject qo);
}
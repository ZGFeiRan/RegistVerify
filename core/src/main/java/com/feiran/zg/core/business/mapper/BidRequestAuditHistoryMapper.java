package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.BidRequestAuditHistory;

import java.util.List;

public interface BidRequestAuditHistoryMapper {

    int insert(BidRequestAuditHistory record);

    BidRequestAuditHistory selectByPrimaryKey(Long id);

    /**
     * 查询出一个借款相关的所有审核历史对象
     * @param id
     * @return
     */
    List<BidRequestAuditHistory> listAuditHistoryByBidRequestId(Long id);
}
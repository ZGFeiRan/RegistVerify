package com.feiran.zg.core.business.mapper;

import com.feiran.zg.core.business.domain.Bid;
import org.apache.ibatis.annotations.Param;

public interface BidMapper {
    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    /**
     * 修改属于某个借款标的所有投标对象的状态
     * @param bidRequestId
     * @param state
     */
    void updateBidsState(@Param("bidRequestId") Long bidRequestId, @Param("state") int state);
}
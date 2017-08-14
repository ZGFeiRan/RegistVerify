package com.feiran.zg.core.business.query;

import com.feiran.zg.core.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 借款查询对象
 * Created by zhangguangfei on 2017/2/9.
 */
@Setter@Getter
public class BidRequestQueryObject extends QueryObject {
    /**
     * 借款状态,按照只有一个状态的条件去查询
     */
    private int bidRequestState = -1;

    /**
     * 按照有多个状态的条件查询
     */
    private int[] states;

    private String orderByColumn;// 按照那一列排序
    private String orderByType;// 按照什么顺序排序(升序、降序)

    public String getOrderByColumn(){
        return StringUtils.hasLength(this.orderByColumn)?this.orderByColumn:null;
    }

    public String getOrderByType(){
        return StringUtils.hasLength(this.orderByType)?this.orderByType:null;
    }
}
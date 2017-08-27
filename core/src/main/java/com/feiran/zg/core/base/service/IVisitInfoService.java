package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.VisitInfo;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.VisitInfoQueryObject;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/26.
 */
public interface IVisitInfoService {
    int deleteByPrimaryKey(String id);

    int insert(VisitInfo record);

    VisitInfo selectByPrimaryKey(String id);

    List<VisitInfo> selectAll();

    int updateByPrimaryKey(VisitInfo record);


    List<VisitInfo> getDoctorVisitInfosByDoctorId(Long publisher_id);

    /**
     * 高级查询加分页
     * @param qo
     * @return
     */
    PageResult queryForPageResult(VisitInfoQueryObject qo);
}

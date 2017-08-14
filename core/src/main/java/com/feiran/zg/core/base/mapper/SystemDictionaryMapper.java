package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.SystemDictionary;
import com.feiran.zg.core.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    /**
     * 查询满足条件的数据的条数
     * @param qo
     * @return
     */
    int queryForCount(SystemDictionaryQueryObject qo);

    /***
     * 插叙满足条件的PageResult
     * @param qo
     * @return
     */
    List<SystemDictionary> queryForListData(SystemDictionaryQueryObject qo);

}
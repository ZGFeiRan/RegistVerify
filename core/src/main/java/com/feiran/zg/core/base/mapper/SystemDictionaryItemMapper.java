package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.SystemDictionaryItem;
import com.feiran.zg.core.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectByParentSn(String sn);

    int updateByPrimaryKey(SystemDictionaryItem record);

    int queryForCount(SystemDictionaryQueryObject qo);

    List<SystemDictionaryItem> queryForListData(SystemDictionaryQueryObject qo);
}
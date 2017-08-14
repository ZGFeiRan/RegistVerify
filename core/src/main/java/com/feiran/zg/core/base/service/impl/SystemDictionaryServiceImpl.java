package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.SystemDictionary;
import com.feiran.zg.core.base.domain.SystemDictionaryItem;
import com.feiran.zg.core.base.mapper.SystemDictionaryItemMapper;
import com.feiran.zg.core.base.mapper.SystemDictionaryMapper;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.SystemDictionaryQueryObject;
import com.feiran.zg.core.base.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/1/18.
 */
@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;
    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public PageResult queryForDictionaryPageResult(SystemDictionaryQueryObject qo) {
        int count = systemDictionaryMapper.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<SystemDictionary> listData = systemDictionaryMapper.queryForListData(qo);

        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void saveOrUpdateSystemDictionary(SystemDictionary systemDictionary) {
        if (systemDictionary.getId()!=null){// 有id值说明是更新
            this.systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
        }else {// 没有id值,说明是新增
            this.systemDictionaryMapper.insert(systemDictionary);
        }
    }

    @Override
    public PageResult queryForDictionaryItemsPageResult(SystemDictionaryQueryObject qo) {

        int count = systemDictionaryItemMapper.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<SystemDictionaryItem> listData  = systemDictionaryItemMapper.queryForListData(qo);

        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void saveOrUpdateSystemDictionaryItem(SystemDictionaryItem systemDictionaryItem) {
        if (systemDictionaryItem.getId()!=null){// 有id值说明是更新
            this.systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
        }else {// 没有id值,说明是新增
            this.systemDictionaryItemMapper.insert(systemDictionaryItem);
        }
    }

    @Override
    public List<SystemDictionary> listForDictionary() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public List<SystemDictionaryItem> loadBySn(String sn) {
        List<SystemDictionaryItem> systemDictionaryItems = this.systemDictionaryItemMapper.selectByParentSn(sn);
        return systemDictionaryItems;
    }


}

package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.query.SystemDictionaryQueryObject;
import com.feiran.zg.core.base.domain.SystemDictionary;
import com.feiran.zg.core.base.domain.SystemDictionaryItem;
import com.feiran.zg.core.base.page.PageResult;

import java.util.List;

/**
 * 数据字典服务相关
 * Created by zhangguangfei on 2017/1/18.
 */
public interface ISystemDictionaryService {
    /***
     * 对数据字典分类进行高级查询
     * @param qo
     * @return
     */
    PageResult queryForDictionaryPageResult(SystemDictionaryQueryObject qo);

    /**
     * 添加或修改数据字典分类
     * @param systemDictionary
     */
    void saveOrUpdateSystemDictionary(SystemDictionary systemDictionary);

    /**
     * 数据字典明细查询
     * @param qo
     * @return
     */
    PageResult queryForDictionaryItemsPageResult(SystemDictionaryQueryObject qo);

    /**
     * 添加或修改数据字典明细
     * @param systemDictionaryItem
     */
    void saveOrUpdateSystemDictionaryItem(SystemDictionaryItem systemDictionaryItem);

    /**
     * 查询出所有的数据字典分类
     * @return
     */
    List<SystemDictionary> listForDictionary();

    /**
     * 根据数据字典的分类编号查询明细
     * @param sn
     * @return
     */
    List<SystemDictionaryItem> loadBySn(String sn);
}

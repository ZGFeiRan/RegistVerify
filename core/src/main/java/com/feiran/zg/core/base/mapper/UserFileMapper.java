package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.UserFile;
import com.feiran.zg.core.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserFile record);

    /**
     * 查询指定用户没有选择文件类型的风控材料
     * @param loginInfoId
     * @return
     */
    List<UserFile> listUnSelectTypeFiles(Long loginInfoId);

    /**
     * 查询指定用户所有的风控材料
     * @param loginInfoId
     * @return
     */
    List<UserFile> listUserFilesByHasSelectType(@Param("loginInfoId") Long loginInfoId, @Param("hasType") boolean hasType);

    /**
     * 查询满足条件的数据的条数
     * @return
     */
    int queryForCount(UserFileQueryObject qo);

    /***
     * 插叙满足条件的PageResult
     * @param qo
     * @return
     */
    List<UserFile> queryForListData(UserFileQueryObject qo);
}
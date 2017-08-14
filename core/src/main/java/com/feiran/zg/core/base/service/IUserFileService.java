package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.domain.UserFile;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.UserFileQueryObject;

import java.util.List;

/**
 * 用户风控资料控制器
 * Created by zhangguangfei on 2017/2/7.
 */
public interface IUserFileService {
    /**
     * 每上传一个风控资料图片,就为其创建一个UserFile对象
     * @param fileName
     */
    void apply(String fileName);

    /**
     * 根据前台出过来的风控材料及其描述文件,更新UserFile对象
     * @param id
     * @param fileType
     */
    void chooseTypeFiles(Long[] id,Long[] fileType);

    /**
     * 根据是否有类型,查询风控材料
     * @param hasType:如果为true,则表示选择了文件类型的;如果为false,则表示没有选择文件类型的
     * @return
     */
    List<UserFile> listUserFilesByHasSelectType(boolean hasType);

    /**
     * 高级查询加分页
     * @param qo
     * @return
     */
    PageResult queryForPageResult(UserFileQueryObject qo);

    /**
     * 直接查询结果集
     * @param qo
     * @return
     */
    List<UserFile> queryForListData(UserFileQueryObject qo);

    /**
     * 风控材料审核
     */
    void audit(Long id,String remark,int score,int state);
}

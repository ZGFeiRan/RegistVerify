package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.domain.UserFile;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.service.IUserFileService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.base.domain.SystemDictionaryItem;
import com.feiran.zg.core.base.mapper.UserFileMapper;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.UserFileQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/7.
 */
@Service
public class UserFileServiceImpl implements IUserFileService {
    @Autowired
    private UserFileMapper userFileMapper;
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public void apply(String fileName) {
        UserFile uf = new UserFile();
        uf.setApplier(UserContext.getCurrent());
        uf.setApplyTime(new Date());
        uf.setImage(fileName);
        uf.setState(UserFile.STATE_NORMAL);
        this.userFileMapper.insert(uf);
    }


    @Override
    public void chooseTypeFiles(Long[] ids, Long[] fileTypes) {
        if (ids!=null && fileTypes!=null && ids.length==fileTypes.length){
            for (int i=0;i<ids.length;i++){
                UserFile userFile = this.userFileMapper.selectByPrimaryKey(ids[i]);
                if (userFile!=null){
                    SystemDictionaryItem item = new SystemDictionaryItem();
                    item.setId(fileTypes[i]);
                    userFile.setFileType(item);
                    this.userFileMapper.updateByPrimaryKey(userFile);
                }
            }
        }
    }

    @Override
    public List<UserFile> listUserFilesByHasSelectType(boolean hasType) {
        return this.userFileMapper.listUserFilesByHasSelectType(UserContext.getCurrent().getId(),hasType);
    }

    @Override
    public PageResult queryForPageResult(UserFileQueryObject qo) {
        int count = this.userFileMapper.queryForCount(qo);
        if (count<=0){
            return PageResult.empty(qo.getPageSize());
        }

        List<UserFile> listData = this.userFileMapper.queryForListData(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int score, int state) {
        // 得到userFile对象,通过状态码判断当前的userFile对象是否处于待审核状态(如果是处于已审核状态或者审核拒绝状态,则直接跳过)
        UserFile userFile = this.userFileMapper.selectByPrimaryKey(id);
        if (userFile!=null && userFile.getState()==UserFile.STATE_NORMAL){
            // 设置userFile相关属性
            userFile.setAuditor(UserContext.getCurrent());
            userFile.setAuditTime(new Date());
            userFile.setRemark(remark);
            userFile.setState(state);
            // 如果审核通过了,累加用户的风控分数
            if (userFile.getState()==UserFile.STATE_AUDIT){
                // 获取申请人
                UserInfo userInfo = this.userInfoService.getById(userFile.getApplier().getId());
                userInfo.setAuthScore(userInfo.getAuthScore()+score);// 设置累加风控分数
                userFile.setScore(score);// 审核通过后,设置userFile的分数
                this.userInfoService.update(userInfo);
            }
            this.userFileMapper.updateByPrimaryKey(userFile);
        }
    }

    @Override
    public List<UserFile> queryForListData(UserFileQueryObject qo) {
        return this.userFileMapper.queryForListData(qo);
    }
}
